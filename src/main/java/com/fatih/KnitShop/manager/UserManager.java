package com.fatih.KnitShop.manager;

import com.fatih.KnitShop.dto.request.user.UserFollowRequest;
import com.fatih.KnitShop.entity.ImageEntity;
import com.fatih.KnitShop.entity.UserEntity;
import com.fatih.KnitShop.exception.DataAlreadyExistException;
import com.fatih.KnitShop.exception.ResourceNotFoundException;
import com.fatih.KnitShop.exception.UnauthorizedException;
import com.fatih.KnitShop.manager.service.UserService;
import com.fatih.KnitShop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import static com.fatih.KnitShop.url.RecordStatus.PASSIVE;

@Service
@RequiredArgsConstructor
public class UserManager implements UserService {

    private final UserRepository userRepository;
    private final MessageSource messageSource;
    private final SoftDeletePost softDeletePost;

    //Checked
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public UserEntity getUserById(UUID userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(messageSource
                .getMessage("backend.exceptions.US001",
                        new Object[]{userId},
                        Locale.getDefault())));
    }

    //Checked
    @Transactional
    @Override
    public void follow(UserFollowRequest userFollowRequest) {
        //Takip edecek kişi
        UserEntity follower = getUserById(userFollowRequest.followerId());

        //Takip edilecek kişi
        UserEntity following = getUserById(userFollowRequest.followingId());

        //Request gönderen kişi takip isteğini gönderen kişiyle aynı mı?
        checkAuthority(userFollowRequest.requesterId(), userFollowRequest.followerId());

        //Takipçi zaten takip ediyor mu?
        validateFollow(follower, following);

        following.getFollowers().add(follower);
        follower.getFollowing().add(following);

        updateFollowCounts(follower, following);

        userRepository.save(follower);
        userRepository.save(following);
    }

    //Checked
    @Transactional
    @Override
    public void unfollow(UUID unfollowerId, UUID unfollowingId, UUID requesterId) {
        UserEntity follower = getUserById(unfollowerId);
        UserEntity following = getUserById(unfollowingId);

        checkAuthority(requesterId, unfollowerId);

        validateUnfollow(follower, following);

        following.getFollowers().remove(follower);
        follower.getFollowing().remove(following);

        updateFollowCounts(follower, following);

        userRepository.save(follower);
        userRepository.save(following);
    }

    //Checked
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<UserEntity> getAllUsers() {

        return userRepository.findAll();
    }

    //Checked
    @Transactional
    @Override
    public UserEntity createUser(UserEntity user) {

        //Username ve email'i kontrol et
        validateUserEmailAndUsername(user.getUsername(), user.getEmail());

        //Alanları tek tek setle
        UserEntity foundUser = UserEntity.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .username(user.getUsername().toLowerCase(Locale.ROOT))
                .email(user.getEmail())
                .password(user.getPassword().toLowerCase(Locale.ROOT))
                .biography(user.getBiography())
                .build();

        //Avatar için yeni bir avatar nesnesi oluşturup kullanıcıya setle
        ImageEntity imageEntity = ImageEntity.builder()
                .imagePath(user.getAvatarImage().getImagePath())
                .user(foundUser)
                .build();

        foundUser.setAvatarImage(imageEntity);

        return userRepository.save(foundUser);
    }

    //Checked
    @Transactional
    @Override
    public UserEntity updateUser(UserEntity requestedUser, UUID requesterId) {

        //Güncellenecek kullanıcıyı kontrol et
        UserEntity foundUser = getUserById(requestedUser.getId());

        //Güncelleyecek kullanıcıyı kontrol et
        checkUser(requesterId);

        //Güncelleyecek ve güncellenecek kullanıcılar aynı kişi mi?
        checkAuthority(requesterId, requestedUser.getId());

        //Güncelleme işlemlerini yapan metot ve kaydet.
        return updateChecks(requestedUser, foundUser);
    }

    //Checked
    @Transactional
    @Override
    public void deleteUser(UUID ownerId, UUID requesterId) {

        //Silinecek user'ım var mı?
        UserEntity foundUser = getUserById(ownerId);

        //Silecek kişi var mı?
        checkUser(requesterId);

        //Silinecek kişi ile silmek iseyen kişi aynı mı?
        checkAuthority(requesterId, ownerId);

        //Bu adamın takip ettikleri ve takipçileri var
        List<UserEntity> followers = getFollowersById(foundUser.getId(), createUnpagedPageable()).getContent();
        List<UserEntity> followings = getFollowingsById(foundUser.getId(), createUnpagedPageable()).getContent();

        //Takipçilerinde gezip her birisi için bu adamı unfollow ettir.
        followers.forEach(follower -> unfollow(follower.getId(), foundUser.getId(), follower.getId()));

        //Takip ettiklerinde gezip her birisini unfollow et
        followings.forEach(following -> unfollow(foundUser.getId(), following.getId(), foundUser.getId()));

        foundUser.getPosts().forEach(softDeletePost::softDeletePost);

        //Silinecek kişi silindi
        foundUser.setRecordStatus(PASSIVE);

        userRepository.save(foundUser);
    }

    //Checked
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<UserEntity> getFollowersById(UUID ownerId, Pageable pageable) {

        UserEntity user = getUserById(ownerId);
        List<UserEntity> followers = user.getFollowers();

        List<UserEntity> content = followers.subList(0, Math.min(followers.size(), pageable.getPageSize()));

        return new PageImpl<>(content, pageable, content.size());
    }

    //Checked
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<UserEntity> getFollowingsById(UUID ownerId, Pageable pageable) {

        UserEntity user = getUserById(ownerId);
        List<UserEntity> followings = user.getFollowing();
        List<UserEntity> content = followings.subList(0, Math.min(followings.size(), pageable.getPageSize()));

        return new PageImpl<>(content, pageable, content.size());
    }

    //Checked
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public void validateFollow(UserEntity follower, UserEntity following) {
        if (following.getFollowers().contains(follower)) {
            throw new DataAlreadyExistException(messageSource.getMessage("backend.exceptions.US002",
                    new Object[]{follower.getId()},
                    Locale.getDefault()));
        }
    }

    //Checked
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public void validateUnfollow(UserEntity follower, UserEntity following) {
        if (!(following.getFollowers().contains(follower))) {
            throw new ResourceNotFoundException(messageSource.getMessage("backend.exceptions.US003",
                    new Object[]{follower.getId()},
                    Locale.getDefault()));
        }
    }

    //Checked
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public void updateFollowCounts(UserEntity follower, UserEntity following) {
        following.setFollowersCount((long) following.getFollowers().size());
        follower.setFollowingCount((long) follower.getFollowing().size());
    }

    //Checked
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public void validateUserEmailAndUsername(String username, String email) {
        //        if (userRepository.existsUserEntityByUsername(username)) {
        //            throw new DataAlreadyExistException(messageSource.getMessage("backend.exceptions.U004",
        //                    new Object[]{username},
        //                    Locale.getDefault()));
        //        } else if (userRepository.existsUserEntityByEmail(email)) {
        //            throw new DataAlreadyExistException(messageSource.getMessage("backend.exceptions.U005",
        //                    new Object[]{email},
        //                    Locale.getDefault()));
        //        }

        userRepository.findByUsername(username).ifPresent(user -> {
            throw new DataAlreadyExistException(messageSource.getMessage("backend.exceptions.US004",
                    new Object[]{username},
                    Locale.getDefault()));
        });

        userRepository.findByEmail(email).ifPresent(user -> {
            throw new DataAlreadyExistException(messageSource.getMessage("backend.exceptions.US005",
                    new Object[]{email},
                    Locale.getDefault()));
        });
    }

    //Checked
    @Transactional
    public UserEntity updateChecks(UserEntity requestedUser, UserEntity foundUser) {
        if (requestedUser.getUsername() != null || requestedUser.getEmail() != null) {
            validateUserEmailAndUsername(requestedUser.getUsername(), requestedUser.getEmail());
            if (requestedUser.getUsername() != null) {
                foundUser.setUsername(requestedUser.getUsername().toLowerCase(Locale.ROOT));
            }
            if (requestedUser.getEmail() != null) {
                foundUser.setEmail(requestedUser.getEmail());
            }
        }
        if (requestedUser.getName() != null) {
            foundUser.setName(requestedUser.getName());
        }
        if (requestedUser.getSurname() != null) {
            foundUser.setSurname(requestedUser.getSurname());
        }
        if (requestedUser.getPassword() != null) {
            foundUser.setPassword(requestedUser.getPassword().toLowerCase(Locale.ROOT));
        }
        if (requestedUser.getBiography() != null) {
            foundUser.setBiography(requestedUser.getBiography());
        }
        if (requestedUser.getAvatarImage() != null && requestedUser.getAvatarImage().getImagePath() != null) {
            foundUser.getAvatarImage().setRecordStatus(PASSIVE);
            ImageEntity newAvatarImage = requestedUser.getAvatarImage();
            foundUser.setAvatarImage(newAvatarImage);
        }
        return userRepository.save(foundUser);
    }

    //Checked
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public void checkAuthority(UUID requesterId, UUID ownerId) {
        if (!(ownerId.equals(requesterId))) {
            throw new UnauthorizedException(messageSource.getMessage("backend.exceptions.US006",
                    new Object[]{requesterId, ownerId},
                    Locale.getDefault()));
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public void checkUser(UUID userId) {
        Optional.of(userRepository.existsById(userId)).filter(exists -> exists).orElseThrow(() ->
                new ResourceNotFoundException(messageSource.getMessage("backend.exceptions.US001",
                        new Object[]{userId},
                        Locale.getDefault())));
    }

    public Pageable createUnpagedPageable() {
        return PageRequest.of(0, Integer.MAX_VALUE, Sort.unsorted());
    }
}