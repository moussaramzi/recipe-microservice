package com.example.recipeservice.controller;

import com.example.recipeservice.dto.RecipeCreateRequest;
import com.example.recipeservice.dto.RecipeUpdateRequest;
import com.example.recipeservice.dto.RecipeResponse;
import com.example.recipeservice.service.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RecipeResponse create(@Validated @RequestBody RecipeCreateRequest request) {
        return recipeService.createRecipe(request);
    }

    @GetMapping
    public List<RecipeResponse> getAll() {
        return recipeService.getAllRecipes();
    }

    @GetMapping("/{id}")
    public RecipeResponse getById(@PathVariable Long id) {
        return recipeService.getRecipeById(id);
    }

    @PutMapping("/{id}")
    public RecipeResponse update(@PathVariable Long id, @Validated @RequestBody RecipeUpdateRequest request) {
        return recipeService.updateRecipe(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
    }
}
