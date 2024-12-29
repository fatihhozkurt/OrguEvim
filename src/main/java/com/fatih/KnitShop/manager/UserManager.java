package com.fatih.KnitShop.manager;

import com.fatih.KnitShop.dto.request.user.UserFollowRequest;
import com.fatih.KnitShop.dto.request.user.UserUnfollowRequest;
import com.fatih.KnitShop.entity.UserEntity;
import com.fatih.KnitShop.exception.DataAlreadyExistException;
import com.fatih.KnitShop.exception.ResourceNotFoundException;
import com.fatih.KnitShop.manager.service.UserService;
import com.fatih.KnitShop.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.UUID;

@Service
@AllArgsConstructor
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
    public void validateFollow(UserEntity follower, UserEntity following) {
        if (following.getFollowers().contains(follower)) {
            throw new DataAlreadyExistException(messageSource.getMessage("backend.exceptions.U002",
                    new Object[]{follower.getId()},
                    Locale.getDefault()));
        }
    }

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
}