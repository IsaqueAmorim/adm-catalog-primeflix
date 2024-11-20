package com.amorim.dev.admin.catalogo.domain.validation;

import java.util.List;

public interface ValidationHandler {
    List<Error> getErrors();
    ValidationHandler append(Error anError);
    ValidationHandler append(ValidationHandler anHandler);
    ValidationHandler validate(Validation aValidation);

    default boolean hasErrors() {
        return getErrors() != null && !getErrors().isEmpty();
    }
    public interface Validation{
        void validate();
    }
}
