package com.amorim.dev.admin.catalogo.application.update;

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
}
