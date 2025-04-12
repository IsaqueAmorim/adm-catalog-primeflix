package com.amorim.dev.admin.catalogo.application.category.create;

import com.amorim.dev.admin.catalogo.application.UseCase;
import com.amorim.dev.admin.catalogo.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class CreateCategoryUseCase
    extends UseCase<CreateCategoryCommand, Either<Notification,CreateCategoryOutput>> {

}
