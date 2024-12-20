package com.example.recipeservice.service;

import com.example.recipeservice.dto.RecipeCreateRequest;
import com.example.recipeservice.dto.RecipeResponse;
import com.example.recipeservice.dto.RecipeUpdateRequest;
import com.example.recipeservice.dto.UserDto;
import com.example.recipeservice.model.Recipe;
import com.example.recipeservice.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final WebClient webClient;

    @Value("${userservice.url:http://user-service:8081}")
    private String userServiceUrl;

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

        // Fetch user info asynchronously using WebClient
        if (recipe.getAuthorId() != null && !recipe.getAuthorId().isEmpty()) {
            try {
                UserDto user = webClient.get()
                        .uri(userServiceUrl + "/api/users/{id}", recipe.getAuthorId())
                        .retrieve()
                        .bodyToMono(UserDto.class)
                        .block();
                response.setAuthor(user);
            } catch (Exception e) {
                // Handle the error gracefully, e.g., log it or leave the author as null
            }
        }

        return response;
    }
}
