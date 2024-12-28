package com.fatih.KnitShop.controller;

import com.fatih.KnitShop.api.CategoryControllerApi;
import com.fatih.KnitShop.dto.response.category.CategoryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.fatih.KnitShop.url.UrlConst.CATEGORY;

@RestController
public class CategoryController implements CategoryControllerApi {
    @Override
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        return null;
    }
}
