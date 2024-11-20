package com.amorim.dev.admin.catalogo.application;


import com.amorim.dev.admin.catalogo.domain.category.Category;

public abstract class UseCase<IN, OUT> {

    public abstract OUT execute(final IN anIn);
}