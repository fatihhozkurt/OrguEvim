package com.fatih.KnitShop.dto.response.post;

import com.fatih.KnitShop.dto.response.image.ImageResponse;

import java.util.List;

public record PostDetailResponse(

        //Post fields
        PostCardResponse postCardResponse,
        String postContent,
        String youtubeLink,

        //Image fields
        List<ImageResponse> postImages
) {
}
