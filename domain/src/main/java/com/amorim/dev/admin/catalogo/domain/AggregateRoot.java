package com.amorim.dev.admin.catalogo.domain;

import com.amorim.dev.admin.catalogo.domain.validation.ValidationHandler;

public abstract class AggregateRoot<ID extends  Identifier> extends Entity<ID> {

    protected AggregateRoot(final ID id) {
        super(id);
    }
}
