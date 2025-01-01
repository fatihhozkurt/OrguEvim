package com.fatih.KnitShop.controller.api;

import com.fatih.KnitShop.dto.request.image.UploadImageRequest;
import com.fatih.KnitShop.dto.response.image.ImageResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.fatih.KnitShop.url.UrlConst.*;

@RequestMapping(IMAGE)
public interface ImageControllerApi {


    @PostMapping
    ResponseEntity<ImageResponse> uploadImage(@Valid @RequestBody List<UploadImageRequest> uploadImageRequest);

    @GetMapping(ALL)
    ResponseEntity<List<ImageResponse>> getAllImages();

    //    @GetMapping(DOWNLOAD)
    //    ResponseEntity<ImageResponse> downloadImage(@RequestParam("imageId") @NotNull UUID imageId);

    @GetMapping(ID)
    ResponseEntity<ImageResponse> getImageById(@RequestParam("imageId") @NotNull UUID imageId);

    @DeleteMapping
    ResponseEntity<HttpStatus> deleteImageById(@RequestParam("imageId") @NotNull UUID imageId);
}
