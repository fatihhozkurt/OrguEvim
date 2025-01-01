package com.fatih.KnitShop.mapper;

import com.fatih.KnitShop.dto.request.category.CreateCategoryRequest;
import com.fatih.KnitShop.dto.request.category.UpdateCategoryRequest;
import com.fatih.KnitShop.dto.response.category.CategoryResponse;
import com.fatih.KnitShop.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    //Checked
    @Mapping(target = "categoryId", source = "id")
    CategoryResponse toCategoryResponse(CategoryEntity categoryEntity);

    //Checked
    List<CategoryResponse> toCategoryResponseList(List<CategoryEntity> categoryEntities);

    //Checked
    CategoryEntity createCategoryRequestToEntity(CreateCategoryRequest createCategoryRequest);

    //Checked
    @Mapping(target = "id", source = "categoryId")
    CategoryEntity updateCategoryRequestToEntity(UpdateCategoryRequest updateCategoryRequest);
}
