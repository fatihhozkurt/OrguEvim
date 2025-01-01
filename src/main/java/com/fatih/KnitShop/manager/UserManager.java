package com.fatih.KnitShop.manager;

import com.fatih.KnitShop.dto.request.user.UserFollowRequest;
import com.fatih.KnitShop.entity.ImageEntity;
import com.fatih.KnitShop.entity.PostEntity;
import com.fatih.KnitShop.entity.UserEntity;
import com.fatih.KnitShop.exception.DataAlreadyExistException;
import com.fatih.KnitShop.exception.ResourceNotFoundException;
import com.fatih.KnitShop.exception.UnauthorizedException;
import com.fatih.KnitShop.manager.service.UserService;
import com.fatih.KnitShop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static com.fatih.KnitShop.url.RecordStatus.PASSIVE;

@Service
@RequiredArgsConstructor
public class UserManager implements UserService {

    private final UserRepository userRepository;
    private final MessageSource messageSource;

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public UserEntity getUserById(UUID userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(messageSource
                .getMessage("backend.exceptions.U001",
                        new Object[]{userId},
                        Locale.getDefault())));
    }

    @Transactional
    @Override
    public void follow(UserFollowRequest userFollowRequest) {
        //Takip edecek kişi
        UserEntity follower = getUserById(userFollowRequest.followerId());

        //Takip edilecek kişi
        UserEntity following = getUserById(userFollowRequest.followingId());

        //Request gönderen kişi isteği gönderen kişiyle aynı mı?
        checkAuthority(userFollowRequest.followerId(), userFollowRequest.requesterId());

        //Takipçi zaten takip ediyor mu?
        validateFollow(follower, following);

        following.getFollowers().add(follower);
        follower.getFollowing().add(following);

        updateFollowCounts(follower, following);

        userRepository.save(follower);
        userRepository.save(following);0
    }

    @Transactional
    @Override
    public void unfollow(UUID unfollowerId, UUID unfollowingId, UUID requesterId) {
        UserEntity follower = getUserById(unfollowerId);
        UserEntity following = getUserById(unfollowingId);

        checkAuthority(unfollowerId, requesterId);

        validateUnfollow(follower, following);

        following.getFollowers().remove(follower);
        follower.getFollowing().remove(following);

        updateFollowCounts(follower, following);

        userRepository.save(follower);
        userRepository.save(following);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<UserEntity> getAllUsers() {

        return userRepository.findAll();
    }

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

    @Transactional
    @Override
    public UserEntity updateUser(UserEntity requestedUser, UUID requesterId) {

        //Güncellenecek kullanıcıyı kontrol et
        UserEntity foundUser = getUserById(requestedUser.getId());

        //Güncelleyecek kullanıcıyı kontrol et
        getUserById(requesterId);

        //Güncelleyecek ve güncellenecek kullanıcılar aynı kişi mi?
        checkAuthority(requestedUser.getId(), requesterId);

        //Güncelleme işlemlerini yapan metot ve kaydet.
        return updateChecks(requestedUser, foundUser);
    }


    @Transactional
    @Override
    public void deleteUser(UUID ownerId, UUID requesterId) {

        //Silinecek user'ım var mı?
        UserEntity foundUser = getUserById(ownerId);
        //Silecek kişi var mı?
        getUserById(requesterId);
        //Silinecek kişi ile silmek iseyen kişi aynı mı?
        checkAuthority(ownerId, requesterId);

        //Bu adamın takip ettikleri ve takipçileri var
        List<UserEntity> followers = getFollowersById(foundUser.getId(), Pageable.unpaged()).getContent();
        List<UserEntity> followings = getFollowingsById(foundUser.getId(), Pageable.unpaged()).getContent();

        //Takipçilerinde gezip her birisi için bu adamı unfollow ettir.
        followers.forEach(follower -> unfollow(follower.getId(), foundUser.getId(), follower.getId()));

        //Takip ettiklerinde gezip her birisini unfollow et
        followings.forEach(following -> unfollow(foundUser.getId(), following.getId(), foundUser.getId()));

        List<PostEntity> posts = foundUser.getPosts();
        posts.forEach(post -> post.setRecordStatus(PASSIVE));
        foundUser.setPosts(posts);

        //Silinecek kişi silindi
        foundUser.setRecordStatus(PASSIVE);

        userRepository.save(foundUser);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<UserEntity> getFollowersById(UUID ownerId, Pageable pageable) {

        UserEntity user = getUserById(ownerId);

        return new PageImpl<>(user.getFollowers(), pageable, user.getFollowers().size());
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<UserEntity> getFollowingsById(UUID ownerId, Pageable pageable) {

        UserEntity user = getUserById(ownerId);

        return new PageImpl<>(user.getFollowing(), pageable, user.getFollowing().size());
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public void validateFollow(UserEntity follower, UserEntity following) {
        if (following.getFollowers().contains(follower)) {
            throw new DataAlreadyExistException(messageSource.getMessage("backend.exceptions.U002",
                    new Object[]{follower.getId()},
                    Locale.getDefault()));
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public void validateUnfollow(UserEntity follower, UserEntity following) {
        if (!(following.getFollowers().contains(follower))) {
            throw new ResourceNotFoundException(messageSource.getMessage("backend.exceptions.U003",
                    new Object[]{follower.getId()},
                    Locale.getDefault()));
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public void updateFollowCounts(UserEntity follower, UserEntity following) {
        following.setFollowersCount((long) following.getFollowers().size());
        follower.setFollowingCount((long) follower.getFollowing().size());
    }

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
            throw new DataAlreadyExistException(messageSource.getMessage("backend.exceptions.U004",
                    new Object[]{username},
                    Locale.getDefault()));
        });

        userRepository.findByEmail(email).ifPresent(user -> {
            throw new DataAlreadyExistException(messageSource.getMessage("backend.exceptions.U005",
                    new Object[]{email},
                    Locale.getDefault()));
        });
    }


    @Transactional
    public UserEntity updateChecks(UserEntity requestedUser, UserEntity foundUser) {
        if (requestedUser.getUsername() != null || requestedUser.getEmail() != null) {
            validateUserEmailAndUsername(requestedUser.getUsername(), requestedUser.getEmail());
        }
        if (requestedUser.getName() != null) {
            foundUser.setName(requestedUser.getName());
        }
        if (requestedUser.getSurname() != null) {
            foundUser.setSurname(requestedUser.getSurname());
        }
        if (requestedUser.getUsername() != null) {
            foundUser.setUsername(requestedUser.getUsername().toLowerCase(Locale.ROOT));
        }
        if (requestedUser.getEmail() != null) {
            foundUser.setEmail(requestedUser.getEmail());
        }
        if (requestedUser.getPassword() != null) {
            foundUser.setPassword(requestedUser.getPassword().toLowerCase(Locale.ROOT));
        }
        if (requestedUser.getBiography() != null) {
            foundUser.setBiography(requestedUser.getBiography());
        }
        if (requestedUser.getAvatarImage().getImagePath() != null) {
            foundUser.getAvatarImage().setRecordStatus(PASSIVE);
            ImageEntity newAvatarImage = requestedUser.getAvatarImage();
            foundUser.setAvatarImage(newAvatarImage);
        }

        return userRepository.save(foundUser);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public void checkAuthority(UUID ownerId, UUID requesterId) {
        if (!(ownerId.equals(requesterId))) {
            throw new UnauthorizedException(messageSource.getMessage("backend.exceptions.U006",
                    new Object[]{requesterId, ownerId},
                    Locale.getDefault()));
        }
    }
}