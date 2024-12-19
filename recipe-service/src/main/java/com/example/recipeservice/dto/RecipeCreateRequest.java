package com.example.recipeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeCreateRequest {
    private String title;
    private String description;
    private List<String> ingredients;
    private List<String> steps;
    private String authorId;
    private String category;
    private List<String> tags;
}
