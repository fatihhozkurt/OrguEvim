package com.fatih.KnitShop.controller;

import com.fatih.KnitShop.controller.api.CategoryControllerApi;
import com.fatih.KnitShop.dto.request.category.CreateCategoryRequest;
import com.fatih.KnitShop.dto.request.category.UpdateCategoryRequest;
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
        List<CategoryEntity> foundCategories = categoryService.getAllCategories();
        List<CategoryResponse> categoryResponses = CategoryMapper.INSTANCE.toCategoryResponseList(foundCategories);
        return new ResponseEntity<>(categoryResponses, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CategoryResponse> createCategory(CreateCategoryRequest createCategoryRequest) {
        CategoryEntity category = CategoryMapper.INSTANCE.createCategoryRequestToEntity(createCategoryRequest);
        CategoryEntity savedCategory = categoryService.createCategory(category);
        CategoryResponse categoryResponse = CategoryMapper.INSTANCE.toCategoryResponse(savedCategory);
        return new ResponseEntity<>(categoryResponse, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<CategoryResponse> getCategoryById(UUID categoryId) {
        CategoryEntity foundCategory = categoryService.getCategoryById(categoryId);
        CategoryResponse categoryResponse = CategoryMapper.INSTANCE.toCategoryResponse(foundCategory);
        return new ResponseEntity<>(categoryResponse, HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteCategoryById(UUID categoryId) {
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<CategoryResponse> updateCategory(UpdateCategoryRequest updateCategoryRequest) {

        CategoryEntity category = CategoryMapper.INSTANCE.updateCategoryRequestToEntity(updateCategoryRequest);
        CategoryEntity updatedCategory = categoryService.updateCategory(category);
        CategoryResponse categoryResponse = CategoryMapper.INSTANCE.toCategoryResponse(updatedCategory);

        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
    }
}