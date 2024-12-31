package com.fatih.KnitShop.manager;

import com.fatih.KnitShop.dto.request.user.UserFollowRequest;
import com.fatih.KnitShop.dto.request.user.UserUnfollowRequest;
import com.fatih.KnitShop.entity.ImageEntity;
import com.fatih.KnitShop.entity.UserEntity;
import com.fatih.KnitShop.exception.DataAlreadyExistException;
import com.fatih.KnitShop.exception.ResourceNotFoundException;
import com.fatih.KnitShop.manager.service.UserService;
import com.fatih.KnitShop.repository.UserRepository;
import lombok.AllArgsConstructor;
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
        UserEntity follower = getUserById(userFollowRequest.followerId());
        UserEntity following = getUserById(userFollowRequest.followingId());

        validateFollow(follower, following);

        following.getFollowers().add(follower);
        follower.getFollowing().add(following);

        updateFollowCounts(follower, following);

        userRepository.save(follower);
        userRepository.save(following);
    }

    @Transactional
    @Override
    public void unfollow(UserUnfollowRequest userUnfollowRequest) {
        UserEntity follower = getUserById(userUnfollowRequest.followerId());
        UserEntity following = getUserById(userUnfollowRequest.followingId());

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

        validateUserEmailAndUsername(user.getUsername(), user.getEmail());

        UserEntity foundUser = UserEntity.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .username(user.getUsername().toLowerCase(Locale.ROOT))
                .email(user.getEmail())
                .password(user.getPassword().toLowerCase(Locale.ROOT))
                .biography(user.getBiography())
                .build();

        ImageEntity imageEntity = ImageEntity.builder()
                .imagePath(user.getAvatarImage().getImagePath())
                .user(foundUser)
                .build();

        foundUser.setAvatarImage(imageEntity);

        return userRepository.save(foundUser);
    }

    @Transactional
    @Override
    public UserEntity updateUser(UserEntity requestedUser) {

        UserEntity foundUser = getUserById(requestedUser.getId());

        updateChecks(requestedUser, foundUser);

        return userRepository.save(foundUser);
    }


    @Transactional
    @Override
    public void deleteUser(UUID userId) {

        UserEntity foundUser = getUserById(userId);

        foundUser.setRecordStatus(PASSIVE);

        userRepository.save(foundUser);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<UserEntity> getFollowersById(UUID userId, Pageable pageable) {

        UserEntity user = getUserById(userId);

        return new PageImpl<>(user.getFollowers(), pageable, user.getFollowers().size());
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<UserEntity> getFollowingsById(UUID userId, Pageable pageable) {

        UserEntity user = getUserById(userId);

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

        userRepository.findUserEntityByUsername(username).ifPresent(user -> {
            throw new DataAlreadyExistException(messageSource.getMessage("backend.exceptions.U004",
                    new Object[]{username},
                    Locale.getDefault()));
        });

        userRepository.findUserEntityByEmail(email).ifPresent(user -> {
            throw new DataAlreadyExistException(messageSource.getMessage("backend.exceptions.U005",
                    new Object[]{email},
                    Locale.getDefault()));
        });
    }


    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public void updateChecks(UserEntity requestedUser, UserEntity foundUser) {
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
            foundUser.getAvatarImage().setImagePath(requestedUser.getAvatarImage().getImagePath());
        }
    }
}