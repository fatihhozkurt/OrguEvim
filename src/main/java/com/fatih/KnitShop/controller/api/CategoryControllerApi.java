package com.fatih.KnitShop.controller.api;

import com.fatih.KnitShop.dto.request.category.CategoryCreateRequest;
import com.fatih.KnitShop.dto.response.category.CategoryResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.fatih.KnitShop.url.UrlConst.*;

@RequestMapping(CATEGORY)
public interface CategoryControllerApi {

    @GetMapping(ALL)
    ResponseEntity<List<CategoryResponse>> getAllCategories();

    @PostMapping
    ResponseEntity<HttpStatus> createCategory(@Valid @RequestBody CategoryCreateRequest categoryCreateRequest);

    @GetMapping(ID)
    ResponseEntity<CategoryResponse> getCategoryById(@RequestParam @NotNull UUID categoryId);

    @DeleteMapping
    ResponseEntity<HttpStatus> deleteCategoryById(@RequestParam @NotNull UUID categoryId);
}
