package com.fatih.KnitShop.dto.response.comment;

import com.fatih.KnitShop.dto.response.user.UserMiniProfileResponse;

import java.util.UUID;

public record ReplyResponse(

        //Comment fields
        UUID replyId,
        Long replyCount,
        String replyContent,

        //User fields
        UserMiniProfileResponse userMiniProfileResponse,

        //Like fields
        Long likeCount
) {
}
