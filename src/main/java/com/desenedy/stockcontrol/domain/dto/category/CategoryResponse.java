package com.desenedy.stockcontrol.domain.dto.category;

public record CategoryResponse(

        Long id,

        String name,

        String description,

        Boolean active

) {
}
