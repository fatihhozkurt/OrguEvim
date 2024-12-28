package com.fatih.KnitShop.dto.response.comment;

import com.fatih.KnitShop.dto.response.user.UserMiniProfile;

import java.util.UUID;

public record CommentResponse(

        //Comment fields
        UUID commentId,
        Long commentCount,
        String commentContent,

        //User fields
        UserMiniProfile userMiniProfile,

        //Like fields
        Long likeCount
) {
}
