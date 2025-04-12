package com.amorim.dev.admin.catalogo.domain.validation.handler;

import com.amorim.dev.admin.catalogo.domain.exceptions.DomainException;
import com.amorim.dev.admin.catalogo.domain.validation.Error;
import com.amorim.dev.admin.catalogo.domain.validation.ValidationHandler;

import java.util.ArrayList;
import java.util.List;

public class Notification implements ValidationHandler {

    private final List<Error> errors;

    private Notification(final List<Error> errors) {
        this.errors = errors;
    }

    public static Notification create() {
        return new Notification(new ArrayList<>());
    }

    public static Notification create(final Throwable error) {
        return create(new Error(error.getMessage()));
    }

    public static Notification create(final Error error) {
        return new Notification(new ArrayList<>()).append(error);
    }

    @Override
    public List<Error> getErrors() {
        return errors;
    }

    @Override
    public Notification append(final Error anError) {
        this.errors.add(anError);
        return this;
    }

    @Override
    public Notification append(final ValidationHandler anHandler) {
        this.errors.addAll(anHandler.getErrors());
        return this;
    }

    @Override
    public Notification validate(Validation aValidation) {
        try{
            aValidation.validate();
        } catch (final DomainException ex){
            this.errors.addAll(ex.getErrors());
        } catch (final Throwable ex){
            this.errors.add(new Error(ex.getMessage()));
        }


        return this;
    }
}
