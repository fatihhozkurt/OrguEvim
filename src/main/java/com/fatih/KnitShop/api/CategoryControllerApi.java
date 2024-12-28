package com.fatih.KnitShop.api;

import com.fatih.KnitShop.dto.response.category.CategoryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static com.fatih.KnitShop.url.UrlConst.ALL;
import static com.fatih.KnitShop.url.UrlConst.CATEGORY;

@RequestMapping(CATEGORY)
public interface CategoryControllerApi {

    @GetMapping(ALL)
    ResponseEntity<List<CategoryResponse>> getAllCategories();

}
