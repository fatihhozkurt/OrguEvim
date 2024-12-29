package com.fatih.KnitShop.manager;

import com.fatih.KnitShop.dto.request.category.CategoryCreateRequest;
import com.fatih.KnitShop.entity.CategoryEntity;
import com.fatih.KnitShop.exception.DataAlreadyExistException;
import com.fatih.KnitShop.exception.ResourceNotFoundException;
import com.fatih.KnitShop.manager.service.CategoryService;
import com.fatih.KnitShop.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static com.fatih.KnitShop.url.RecordStatus.ACTIVE;
import static com.fatih.KnitShop.url.RecordStatus.PASSIVE;

@Service
@RequiredArgsConstructor
public class CategoryManager implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final MessageSource messageSource;

    @Transactional
    @Override
    public void createCategory(String categoryName) {
        categoryRepository.findByCategoryName(categoryName).ifPresent(entity -> {
            throw new DataAlreadyExistException(messageSource.getMessage("backend.exceptions.C002",
                    new Object[]{categoryName},
                    Locale.getDefault()));
        });

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCategoryName(categoryName);
        categoryEntity.setRecordStatus(ACTIVE);

        categoryRepository.save(categoryEntity);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public CategoryEntity getCategoryById(UUID categoryId) {

        return categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException(messageSource.getMessage("backend.exceptions.C001",
                        new Object[]{categoryId},
                        Locale.getDefault())));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<CategoryEntity> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteCategory(UUID categoryId) {
        CategoryEntity category = getCategoryById(categoryId);
        category.setRecordStatus(PASSIVE);
        category.getPosts().clear();
        category.setPostCount(0L);

        categoryRepository.save(category);
    }
}