package com.amorim.dev.admin.catalogo;

import com.amorim.dev.admin.catalogo.domain.category.Category;
import com.amorim.dev.admin.catalogo.domain.exceptions.DomainException;
import com.amorim.dev.admin.catalogo.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CategoryTest {

    @Test
    public void givenValidParams_whenCallNewCategory_thenInstantiateCategory() {

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais Assistida";
        final var expectedIsActive = true;

        final var actualCategory = Category.newCategory(expectedName,expectedDescription,expectedIsActive);

        Assertions.assertNotNull(actualCategory);
        Assertions.assertNotNull(actualCategory.getId());
        Assertions.assertEquals(expectedName,actualCategory.getName());
        Assertions.assertEquals(expectedDescription,actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive,actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());

    }

    @Test
    public void givenInvalidNullName_whenCallNewCategoryAndValidate_thenShouldRecivedError() {

        final String expectedName = null;
        final var expectedDescription = "A categoria mais Assistida";
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;
        final var expectedIsActive = true;

        final var actualCategory = Category.newCategory(expectedName,expectedDescription,expectedIsActive);

        final var actualException = Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));
        Assertions.assertEquals(expectedErrorMessage,actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());


    }

    @Test
    public void givenInvalidEmptyName_whenCallNewCategoryAndValidate_thenShouldRecivedError() {

        final String expectedName = "   ";
        final var expectedDescription = "A categoria mais Assistida";
        final var expectedErrorMessage = "'name' should not be blank";
        final var expectedErrorCount = 1;
        final var expectedIsActive = true;

        final var actualCategory = Category.newCategory(expectedName,expectedDescription,expectedIsActive);

        final var actualException = Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));
        Assertions.assertEquals(expectedErrorMessage,actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());


    }

    @Test
    public void givenInvalidNameLengthLessThan3_whenCallNewCategoryAndValidate_thenShouldRecivedError() {

        final String expectedName = "Fi ";
        final var expectedDescription = "A categoria mais Assistida";
        final var expectedErrorMessage = "'name' should be between 3 and 255 characters";
        final var expectedErrorCount = 1;
        final var expectedIsActive = true;

        final var actualCategory = Category.newCategory(expectedName,expectedDescription,expectedIsActive);

        final var actualException = Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));
        Assertions.assertEquals(expectedErrorMessage,actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());


    }

    @Test
    public void givenInvalidNameLengthMoreThan255_whenCallNewCategoryAndValidate_thenShouldRecivedError() {

        final String expectedName =
          """
          O empenho em analisar a consulta aos diversos militantes auxilia a preparação e a composição do retorno esperado a longo prazo.
          Gostaria de enfatizar que o novo modelo estrutural aqui preconizado garante a contribuição de um grupo importante na determinação de alternativas às soluções ortodoxas.
          A prática cotidiana prova que o desafiador cenário globalizado exige a precisão e a definição dos conhecimentos estratégicos para atingir a excelência.
          O que temos que ter sempre em mente é que a constante divulgação das informações facilita a criação das condições financeiras e administrativas exigidas.
          """;
        final var expectedDescription = "A categoria mais Assistida";
        final var expectedErrorMessage = "'name' should be between 3 and 255 characters";
        final var expectedErrorCount = 1;
        final var expectedIsActive = true;

        final var actualCategory = Category.newCategory(expectedName,expectedDescription,expectedIsActive);

        final var actualException = Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));
        Assertions.assertEquals(expectedErrorMessage,actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());


    }

    @Test
    public void givenValidEmptyDescription_whenCallNewCategoryAndValidate_thenShouldRecivedError() {

        final String expectedName = "Filme";
        final var expectedDescription = "A categoria mais Assistida";
        final var expectedIsActive = true;

        final var actualCategory = Category.newCategory(expectedName,expectedDescription,expectedIsActive);

        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));
        Assertions.assertNotNull(actualCategory);
        Assertions.assertNotNull(actualCategory.getId());
        Assertions.assertEquals(expectedName,actualCategory.getName());
        Assertions.assertEquals(expectedDescription,actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive,actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenValidFalseIsActive_whenCallNewCategoryAndValidate_thenShouldRecivedError() {

        final String expectedName = "Filme";
        final var expectedDescription = "A categoria mais Assistida";
        final var expectedIsActive = false;

        final var actualCategory = Category.newCategory(expectedName,expectedDescription,expectedIsActive);

        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));
        Assertions.assertNotNull(actualCategory);
        Assertions.assertNotNull(actualCategory.getId());
        Assertions.assertEquals(expectedName,actualCategory.getName());
        Assertions.assertEquals(expectedDescription,actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive,actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenValidTrueIsActive_whenCallNewCategoryAndValidate_thenShouldRecivedError() {

        final String expectedName = "Filme";
        final var expectedDescription = "A categoria mais Assistida";
        final var expectedIsActive = true;

        final var actualCategory = Category.newCategory(expectedName,expectedDescription,true);

        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));
        Assertions.assertNotNull(actualCategory);
        Assertions.assertNotNull(actualCategory.getId());
        Assertions.assertEquals(expectedName,actualCategory.getName());
        Assertions.assertEquals(expectedDescription,actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive,actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenValidActiveCategory_whenCallDeactivate_thenReturnInactiveCategory() {
        final String expectedName = "Filme";
        final var expectedDescription = "A categoria mais Assistida";
        final var expectedIsActive = false;

        final var aCategory = Category.newCategory(expectedName,expectedDescription,true);

        final var uptadatedAt = aCategory.getUpdatedAt();

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));
        Assertions.assertTrue(aCategory.isActive());
        Assertions.assertNull(aCategory.getDeletedAt());

        final var createdAt = aCategory.getCreatedAt();
        final var actualCategory = aCategory.deactivate();

        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));
        Assertions.assertEquals(aCategory.getId(),actualCategory.getId());
        Assertions.assertEquals(expectedName,actualCategory.getName());
        Assertions.assertEquals(expectedDescription,actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive,actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertEquals(createdAt,actualCategory.getCreatedAt());
        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(uptadatedAt));
        Assertions.assertNotNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenValidInactiveCategory_whenCallActivate_thenReturnActiveCategory() {
        final String expectedName = "Filme";
        final var expectedDescription = "A categoria mais Assistida";
        final var expectedIsActive = true;

        final var aCategory = Category.newCategory(expectedName,expectedDescription,false);

        final var createdAt = aCategory.getCreatedAt();
        final var uptadatedAt = aCategory.getUpdatedAt();

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));
        Assertions.assertFalse(aCategory.isActive());
        Assertions.assertNotNull(aCategory.getDeletedAt());

        final var actualCategory = aCategory.activate();

        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));
        Assertions.assertEquals(aCategory.getId(),actualCategory.getId());
        Assertions.assertEquals(expectedName,actualCategory.getName());
        Assertions.assertEquals(expectedDescription,actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive,actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertEquals(createdAt,actualCategory.getCreatedAt());
        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(uptadatedAt));
        Assertions.assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void giverValidCategory_whenCallUpdate_thenReturnCategoryUpdated() {
        final String expectedName = "Filme";
        final var expectedDescription = "A categoria mais Assistida";
        final var expectedIsActive = true;

        final var aCategory = Category.newCategory(expectedName,expectedDescription,false);

        final var createdAt = aCategory.getCreatedAt();
        final var uptadatedAt = aCategory.getUpdatedAt();

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));
    }
}
