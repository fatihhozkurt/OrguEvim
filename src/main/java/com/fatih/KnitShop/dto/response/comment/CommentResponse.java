package com.fatih.KnitShop.dto.response.comment;

import com.fatih.KnitShop.dto.response.user.UserMiniProfileResponse;

import java.util.UUID;

public record CommentResponse(

        //Comment fields
        UUID commentId,
        Long commentCount,
        String commentContent,

        //User fields
        UserMiniProfileResponse userMiniProfileResponse,

        //Like fields
        Long likeCount
) {
}
