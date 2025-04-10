package com.amorim.dev.admin.catalogo.application.update;

import com.amorim.dev.admin.catalogo.domain.category.CategoryID;

public record UpdateCategoryCommand(
        CategoryID id,
        String name,
        String description,
        boolean isActive
) {
    public static UpdateCategoryCommand with(
            final CategoryID anId,
            final String aName,
            final String aDescription,
            final boolean isActive
    ) {
        return new UpdateCategoryCommand(anId, aName, aDescription, isActive);
    }
}
