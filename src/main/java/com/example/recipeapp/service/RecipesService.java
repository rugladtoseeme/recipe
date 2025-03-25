package com.example.recipeapp.service;

import com.example.recipeapp.model.Ingredient;
import com.example.recipeapp.model.Recipe;
import com.example.recipeapp.repository.IngredientRepository;
import com.example.recipeapp.repository.RecipeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RecipesService {

    private final RecipeRepository repository;
    private final IngredientRepository ingredientRepository;

    @Autowired
    public RecipesService(RecipeRepository repository, IngredientRepository ingredientRepository) {
        this.repository = repository;
        this.ingredientRepository = ingredientRepository;
    }

    public ArrayList<Recipe> findAllRecipes() {

        return repository.findAll();
    }

    public Recipe getById(int id) {
        Recipe recipeById = findById(id).isPresent() ? findById(id).get() : null;
        if (recipeById == null) {
            //здесь работаем с апишкой
            System.out.println("No recipe found with id " + id + " in db");

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://recipes.androidsprint.ru/api/recipe/" + id))
                    .GET()
                    .build();

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = null;
            try {
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
                String jsonResult = response.body();
                System.out.println(jsonResult);

                ObjectMapper mapper = new ObjectMapper();

                Recipe recipe = mapper.readValue(jsonResult, Recipe.class);

                saveRecipe(recipe);
                return recipe;

            } catch (IOException | InterruptedException e) {
                //throw new RuntimeException(e);
            }
        }
            return recipeById;

        }


    public Optional<Recipe> findById(Integer id) {
        return repository.findById(id);
    }

    @Transactional
    public void saveRecipe(Recipe recipe) {
        Set<String> descriptions = recipe.getIngredients().stream()
                                                            .map(Ingredient::getDescription)
                                                            .collect(Collectors.toSet());

        List<Ingredient> existingIngredients = ingredientRepository.findAllByDescriptionIn(descriptions);

        Map<String, Ingredient> existingIngredientsMap = existingIngredients.stream()
                .collect(Collectors.toMap(Ingredient::getDescription, Function.identity()));

        Set<Ingredient> managedIngredients = new HashSet<>();

        for (Ingredient ingredient : recipe.getIngredients()) {
            Ingredient existingIngredient = existingIngredientsMap.get(ingredient.getDescription());
            if (existingIngredient != null) {
                managedIngredients.add(existingIngredient);
            } else {
                managedIngredients.add(ingredient);
            }
        }

        recipe.setIngredients(managedIngredients);

        repository.save(recipe);

    }
}
