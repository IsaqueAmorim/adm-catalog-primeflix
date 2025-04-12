package com.amorim.dev.admin.catalogo.application.category.update;

import com.amorim.dev.admin.catalogo.application.category.create.CreateCategoryCommand;
import com.amorim.dev.admin.catalogo.application.update.DefaultUpdateCategoryUseCase;
import com.amorim.dev.admin.catalogo.application.update.UpdateCategoryCommand;
import com.amorim.dev.admin.catalogo.domain.category.Category;
import com.amorim.dev.admin.catalogo.domain.category.CategoryGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateCategoryUseCaseTest {

    @InjectMocks
    private DefaultUpdateCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @Test
    public void givenAValidCommand_whenCallUpdateCategory_shouldReturnCategoryId(){
        final var aCategory = Category.newCategory("Film",null,true);

        final var expectedName = "Filme";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedId = aCategory.getId();

        final var aCommand = UpdateCategoryCommand.with(
                aCategory.getId(),
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        when(categoryGateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(aCategory.clone()));

        when(categoryGateway.update(any()))
                .thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        verify(categoryGateway,times(1)).findById(expectedId);
        verify(categoryGateway,times(1)).update(argThat(
                aUpdatedCategory -> Objects.equals(expectedName, aUpdatedCategory.getName())
                    && Objects.equals(expectedDescription, aUpdatedCategory.getDescription())
                    && Objects.equals(expectedIsActive, aUpdatedCategory.isActive())
                    && Objects.equals(expectedId, aUpdatedCategory.getId())
                    && Objects.equals(aCategory.getCreatedAt(), aUpdatedCategory.getCreatedAt())
                    && aCategory.getUpdatedAt().isBefore(aUpdatedCategory.getUpdatedAt())
                    && Objects.isNull(aUpdatedCategory.getDeletedAt())));
    }

    @Test
    public void givenAInvalidNullName_whenCallsUpdateCategory_shouldReturnDomainException(){

        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        final var aCategory = Category.newCategory("Film","A mais assistida",true);

        when(categoryGateway.findById(eq(aCategory.getId())))
                .thenReturn(Optional.of(aCategory));

        final var aCommand = UpdateCategoryCommand.with(aCategory.getId(),null,expectedDescription,expectedIsActive);

        final var notification = useCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());

        Mockito.verify(categoryGateway, Mockito.times(0)).update(Mockito.any());
    }

    @Test
    public void givenAValidInactivateCommand_whenCallUpdateCategory_shouldReturnInactiveCategoryId(){
        final var expectedName = "Filme";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = false;

        final var aCategory = Category.newCategory("Filme","A mais assistida",true);

        Assertions.assertTrue(aCategory.isActive());
        Assertions.assertNull(aCategory.getDeletedAt());

        when(categoryGateway.findById(eq(aCategory.getId())))
                .thenReturn(Optional.of(aCategory));

        when(categoryGateway.update(any()))
                .thenAnswer(returnsFirstArg());

        final var aCommand = UpdateCategoryCommand.with(aCategory.getId(),"Filme",expectedDescription,expectedIsActive);

        useCase.execute(aCommand);

        Assertions.assertFalse(aCategory.isActive());
        Assertions.assertNotNull(aCategory.getDeletedAt());

        Mockito.verify(categoryGateway, Mockito.times(1))
                .update(Mockito.argThat(aCategoryUpdated -> {
                    return Objects.equals(expectedName, aCategoryUpdated.getName())
                            && Objects.equals(expectedDescription, aCategoryUpdated.getDescription())
                            && Objects.equals(expectedIsActive, aCategoryUpdated.isActive())
                            && Objects.nonNull(aCategoryUpdated.getId())
                            && Objects.nonNull(aCategoryUpdated.getCreatedAt())
                            && Objects.nonNull(aCategoryUpdated.getUpdatedAt())
                            && Objects.nonNull(aCategoryUpdated.getDeletedAt());
                }));
    }
}
