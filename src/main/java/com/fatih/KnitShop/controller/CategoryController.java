package com.fatih.KnitShop.controller;

import com.fatih.KnitShop.controller.api.CategoryControllerApi;
import com.fatih.KnitShop.dto.response.category.CategoryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController implements CategoryControllerApi {
    @Override
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        return null;
    }
}
