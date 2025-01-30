package com.fatih.KnitShop.controller.api;

import com.fatih.KnitShop.dto.request.category.CreateCategoryRequest;
import com.fatih.KnitShop.dto.request.category.UpdateCategoryRequest;
import com.fatih.KnitShop.dto.response.category.CategoryResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.fatih.KnitShop.consts.UrlConst.*;

@RequestMapping(CATEGORY)
public interface CategoryControllerApi {

    //CheckedX
    @GetMapping(ALL)
    ResponseEntity<List<CategoryResponse>> getAllCategories();

    //CheckedX
    @PostMapping
    ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CreateCategoryRequest createCategoryRequest);

    //CheckedX
    @GetMapping(ID)
    ResponseEntity<CategoryResponse> getCategoryById(@RequestParam("categoryId") @NotNull UUID categoryId);

    //CheckedX
    @DeleteMapping
    ResponseEntity<HttpStatus> deleteCategoryById(@RequestParam("categoryId") @NotNull UUID categoryId);

    //CheckedX
    @PutMapping
    ResponseEntity<CategoryResponse> updateCategory(@Valid @RequestBody UpdateCategoryRequest updateCategoryRequest);
}
