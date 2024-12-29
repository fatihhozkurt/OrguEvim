package com.fatih.KnitShop.mapper;

import com.fatih.KnitShop.dto.request.category.CategoryCreateRequest;
import com.fatih.KnitShop.dto.response.category.CategoryResponse;
import com.fatih.KnitShop.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    @Mapping(target = "categoryId", source = "id")
    CategoryResponse toCategoryResponse(CategoryEntity categoryEntity);

    List<CategoryResponse> toCategoryResponseList(List<CategoryEntity> categoryEntities);

    CategoryEntity toEntity(CategoryCreateRequest categoryCreateRequest);
}
