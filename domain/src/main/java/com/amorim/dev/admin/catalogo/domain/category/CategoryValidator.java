package com.amorim.dev.admin.catalogo.domain.category;

import com.amorim.dev.admin.catalogo.domain.validation.Error;
import com.amorim.dev.admin.catalogo.domain.validation.ValidationHandler;
import com.amorim.dev.admin.catalogo.domain.validation.Validator;

public class CategoryValidator extends Validator {

    public static final int NAME_MAX_LENGTH = 255;
    public static final int NAME_MIN_LENGTH = 3;
    private final Category category;
    public CategoryValidator(final Category aCategory, final ValidationHandler aValidationHandler) {
        super(aValidationHandler);
        this.category = aCategory;
    }
    @Override
    public void validate() {
        checkNameConstraints();
    }

    private void checkNameConstraints() {
        final var name = category.getName();

        if( name == null) {
            this.validationHandler().append(new Error("'name' should not be null"));
            return;
        }

        if(name.isBlank()){
            this.validationHandler().append(new Error("'name' should not be blank"));
        }

        final int length = name.trim().length();
        if(length > NAME_MAX_LENGTH || length < NAME_MIN_LENGTH) {
            this.validationHandler().append(new Error("'name' should be between 3 and 255 characters"));
        };

    }
}
