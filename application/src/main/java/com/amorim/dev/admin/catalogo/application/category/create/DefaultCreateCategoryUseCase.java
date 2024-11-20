package com.amorim.dev.admin.catalogo.application.category.create;

import com.amorim.dev.admin.catalogo.domain.category.Category;
import com.amorim.dev.admin.catalogo.domain.category.CategoryGateway;
import com.amorim.dev.admin.catalogo.domain.validation.validation.ThrowsValidationHandler;

import java.util.Objects;

public class DefaultCreateCategoryUseCase extends CreateCategoryUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultCreateCategoryUseCase(CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public CreateCategoryOutput execute(final CreateCategoryCommand aComand) {
        final var aName = aComand.name();
        final var aDescription = aComand.description();
        final var isActive = aComand.isActive();

        final var aCategory = Category.newCategory(aName, aDescription, isActive);
        aCategory.validate(new ThrowsValidationHandler());

        return CreateCategoryOutput.from(this.categoryGateway.create(aCategory));
    }
}
