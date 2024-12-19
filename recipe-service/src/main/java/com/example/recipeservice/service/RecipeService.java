package com.example.recipeservice.service;

import com.example.recipeservice.dto.RecipeCreateRequest;
import com.example.recipeservice.dto.RecipeResponse;
import com.example.recipeservice.dto.RecipeUpdateRequest;
import com.example.recipeservice.dto.UserDto;
import com.example.recipeservice.model.Recipe;
import com.example.recipeservice.repository.RecipeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RestTemplate restTemplate;
    private final String userServiceUrl;

    public RecipeService(RecipeRepository recipeRepository, RestTemplate restTemplate,
                         org.springframework.core.env.Environment env) {
        this.recipeRepository = recipeRepository;
        this.restTemplate = restTemplate;
        this.userServiceUrl = env.getProperty("userservice.url", "http://user-service:8081");
    }

    public RecipeResponse createRecipe(RecipeCreateRequest request) {
        Recipe recipe = new Recipe();
        recipe.setTitle(request.getTitle());
        recipe.setDescription(request.getDescription());
        recipe.setIngredients(request.getIngredients());
        recipe.setSteps(request.getSteps());
        recipe.setAuthorId(request.getAuthorId());
        recipe.setCategory(request.getCategory());
        recipe.setTags(request.getTags());
        recipe.setCreatedAt(Instant.now());
        recipe.setUpdatedAt(Instant.now());

        Recipe saved = recipeRepository.save(recipe);
        return mapToResponse(saved);
    }

    public List<RecipeResponse> getAllRecipes() {
        return recipeRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public RecipeResponse getRecipeById(Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found"));
        return mapToResponse(recipe);
    }

    public RecipeResponse updateRecipe(Long id, RecipeUpdateRequest request) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found"));

        recipe.setTitle(request.getTitle());
        recipe.setDescription(request.getDescription());
        recipe.setIngredients(request.getIngredients());
        recipe.setSteps(request.getSteps());
        recipe.setAuthorId(request.getAuthorId());
        recipe.setCategory(request.getCategory());
        recipe.setTags(request.getTags());
        recipe.setUpdatedAt(Instant.now());

        Recipe updated = recipeRepository.save(recipe);
        return mapToResponse(updated);
    }

    public void deleteRecipe(Long id) {
        if (!recipeRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found");
        }
        recipeRepository.deleteById(id);
    }

    private RecipeResponse mapToResponse(Recipe recipe) {
        RecipeResponse response = new RecipeResponse();
        response.setId(recipe.getId());
        response.setTitle(recipe.getTitle());
        response.setDescription(recipe.getDescription());
        response.setIngredients(recipe.getIngredients());
        response.setSteps(recipe.getSteps());
        response.setCategory(recipe.getCategory());
        response.setTags(recipe.getTags());
        response.setCreatedAt(recipe.getCreatedAt());
        response.setUpdatedAt(recipe.getUpdatedAt());

        // Fetch user info directly in the service
        if (recipe.getAuthorId() != null && !recipe.getAuthorId().isEmpty()) {
            try {
                UserDto user = restTemplate.getForObject(userServiceUrl + "/api/users/" + recipe.getAuthorId(), UserDto.class);
                response.setAuthor(user);
            } catch (Exception e) {
                // If unable to fetch user info, handle the error or leave author as null
            }
        }

        return response;
    }
}
