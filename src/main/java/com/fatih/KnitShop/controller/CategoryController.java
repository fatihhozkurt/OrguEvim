package com.fatih.KnitShop.controller;

import com.fatih.KnitShop.controller.api.CategoryControllerApi;
import com.fatih.KnitShop.dto.request.category.CategoryCreateRequest;
import com.fatih.KnitShop.dto.response.category.CategoryResponse;
import com.fatih.KnitShop.entity.CategoryEntity;
import com.fatih.KnitShop.manager.service.CategoryService;
import com.fatih.KnitShop.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CategoryController implements CategoryControllerApi {

    private final CategoryService categoryService;

    @Override
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<CategoryEntity> categories = categoryService.getAllCategories();
        List<CategoryResponse> categoryResponses = CategoryMapper.INSTANCE.toCategoryResponseList(categories);
        return new ResponseEntity<>(categoryResponses, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HttpStatus> createCategory(CategoryCreateRequest categoryCreateRequest) {
        CategoryEntity category = CategoryMapper.INSTANCE.toEntity(categoryCreateRequest);
        categoryService.createCategory(category.getCategoryName());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<CategoryResponse> getCategoryById(UUID categoryId) {
        CategoryEntity category = categoryService.getCategoryById(categoryId);
        CategoryResponse categoryResponse = CategoryMapper.INSTANCE.toCategoryResponse(category);
        return new ResponseEntity<>(categoryResponse, HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteCategoryById(UUID categoryId) {
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}