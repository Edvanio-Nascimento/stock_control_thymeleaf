package com.desenedy.stockcontrol.domain.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryUpdate(

        @NotBlank(message = "Fiels is requerid.")
        @Size(min = 5, max = 100, message = "The name must contain between 5 and 100 characters.")
        String name,

        @NotBlank(message = "Fiels is requerid.")
        @Size(min = 10, max = 500, message = "The name must contain between 10 and 500 characters.")
        String description

) {
}
