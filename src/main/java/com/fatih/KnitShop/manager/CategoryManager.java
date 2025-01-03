package com.fatih.KnitShop.manager;

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

import static com.fatih.KnitShop.url.RecordStatus.PASSIVE;

@Service
@RequiredArgsConstructor
public class CategoryManager implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final MessageSource messageSource;
    private final SoftDeletePostRelationsManager softDeletePostRelationsManager;

    //Checked
    @Transactional
    @Override
    public CategoryEntity createCategory(CategoryEntity category) {

        checkCategoryName(category.getCategoryName());

        return categoryRepository.save(CategoryEntity.builder()
                .categoryName(category.getCategoryName().toLowerCase(Locale.ROOT))
                .build());
    }

    //Checked
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public CategoryEntity getCategoryById(UUID categoryId) {

        return categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException(messageSource.getMessage("backend.exceptions.CAT001",
                        new Object[]{categoryId},
                        Locale.getDefault())));
    }

    //Checked
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<CategoryEntity> getAllCategories() {
        return categoryRepository.findAll();
    }

    //Checked
    @Transactional
    @Override
    public void deleteCategory(UUID categoryId) {

        CategoryEntity category = getCategoryById(categoryId);

        category.getPosts().forEach(softDeletePostRelationsManager::softDeletePostRelations);

        category.setRecordStatus(PASSIVE);

        categoryRepository.save(category);
    }

    //Checked
    @Transactional
    @Override
    public CategoryEntity updateCategory(CategoryEntity category) {

        CategoryEntity foundCategory = getCategoryById(category.getId());

        if (category.getCategoryName() != null) {
            checkCategoryName(category.getCategoryName());
            foundCategory.setCategoryName(category.getCategoryName());
        }

        return categoryRepository.save(foundCategory);
    }

    //Checked
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public void checkCategoryName(String categoryName) {
        if (categoryRepository.findByCategoryName(categoryName.toLowerCase(Locale.ROOT)).isPresent()) {
            throw new DataAlreadyExistException(messageSource.getMessage("backend.exceptions.CAT002",
                    new Object[]{categoryName},
                    Locale.getDefault()));
        }
    }
}