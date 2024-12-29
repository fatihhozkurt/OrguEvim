package com.fatih.KnitShop.manager.service;

import com.fatih.KnitShop.entity.CategoryEntity;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    void createCategory(String categoryName);

    CategoryEntity getCategoryById(UUID categoryId);

    List<CategoryEntity> getAllCategories();

    void deleteCategory(UUID categoryId);
}
