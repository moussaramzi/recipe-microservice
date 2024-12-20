package com.example.recipeservice;

import com.example.recipeservice.dto.RecipeCreateRequest;
import com.example.recipeservice.dto.RecipeResponse;
import com.example.recipeservice.model.Recipe;
import com.example.recipeservice.repository.RecipeRepository;
import com.example.recipeservice.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private WebClient webClient;

    @InjectMocks
    private RecipeService recipeService;

    private RecipeCreateRequest createRequest;
    private Recipe sampleRecipe;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        createRequest = new RecipeCreateRequest();
        createRequest.setTitle("Spaghetti Carbonara");
        createRequest.setDescription("A classic Italian pasta dish.");
        createRequest.setIngredients(List.of("Spaghetti", "Eggs", "Parmesan", "Pancetta"));
        createRequest.setSteps(List.of("Boil pasta", "Cook pancetta", "Mix with eggs and cheese"));
        createRequest.setAuthorId("123");
        createRequest.setCategory("Pasta");
        createRequest.setTags(List.of("Italian", "Dinner"));

        sampleRecipe = new Recipe();
        sampleRecipe.setId(1L);
        sampleRecipe.setTitle("Spaghetti Carbonara");
        sampleRecipe.setDescription("A classic Italian pasta dish.");
        sampleRecipe.setIngredients(createRequest.getIngredients());
        sampleRecipe.setSteps(createRequest.getSteps());
        sampleRecipe.setAuthorId("123");
        sampleRecipe.setCategory("Pasta");
        sampleRecipe.setTags(createRequest.getTags());
    }

    @Test
    void testCreateRecipe() {
        when(recipeRepository.save(any(Recipe.class))).thenReturn(sampleRecipe);

        RecipeResponse response = recipeService.createRecipe(createRequest);

        assertNotNull(response);
        assertEquals("Spaghetti Carbonara", response.getTitle());
        assertEquals("A classic Italian pasta dish.", response.getDescription());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    void testGetRecipeById() {
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(sampleRecipe));

        RecipeResponse response = recipeService.getRecipeById(1L);

        assertNotNull(response);
        assertEquals("Spaghetti Carbonara", response.getTitle());
        assertEquals("Pasta", response.getCategory());
        verify(recipeRepository, times(1)).findById(1L);
    }
}
