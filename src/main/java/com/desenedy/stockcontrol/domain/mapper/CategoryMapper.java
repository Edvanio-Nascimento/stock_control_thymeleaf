package com.desenedy.stockcontrol.domain.mapper;

import com.desenedy.stockcontrol.domain.dto.category.CategoryRequest;
import com.desenedy.stockcontrol.domain.dto.category.CategoryResponse;
import com.desenedy.stockcontrol.domain.dto.category.CategoryUpdate;
import com.desenedy.stockcontrol.domain.entity.Category;

public class CategoryMapper {

    public static Category toEntity(CategoryRequest request) {
        return Category.builder()
                .name(request.name())
                .description(request.description())
                .build();
    }

    public static CategoryResponse toResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getActive()
        );
    }

    public static void toUpdate(CategoryUpdate update, Category category) {

        category.setName(update.name());
        category.setDescription(update.description());

    }

}
