package com.amorim.dev.admin.catalogo.domain.category;

import com.amorim.dev.admin.catalogo.domain.pagination.Pagination;

import java.util.Optional;

public interface CategoryGateway {

    Category create(Category category);
    void deleteById(CategoryID categoryID);
    Optional<Category> findById(CategoryID categoryID);
    Category update(Category category);
    Pagination<Category> findAll(CategorySearchQuery aQuery);
}
