package com.amorim.dev.admin.catalogo.domain.exceptions;

import java.util.List;
import com.amorim.dev.admin.catalogo.domain.validation.Error;

public class DomainException extends NoStackTraceException {

    private final List<Error> errors;
    private DomainException(final String aMessage,List<Error> anErrors) {
        super(aMessage);

        this.errors = anErrors;
    }

    public static DomainException with(final List<Error> anErrors) {
        return new DomainException("",anErrors);
    }

    public static DomainException with(Error anError) {
        return new DomainException(anError.message(),List.of(anError));
    }

    public List<Error> getErrors() {
        return errors;
    }
}
