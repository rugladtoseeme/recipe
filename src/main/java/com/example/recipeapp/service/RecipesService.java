package com.example.recipeapp.service;

import com.example.recipeapp.model.Recipe;
import com.example.recipeapp.repository.RecipeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class RecipesService {

    private final RecipeRepository repository;

    @Autowired
    public RecipesService(RecipeRepository repository) {
        this.repository = repository;
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
                throw new RuntimeException(e);
            }
        }
            return recipeById;

            //здесь проверим если есть в бд с этим айди то получаем из бд, выводим
            //если нет такого в бд то получаем из апишки, записываем в бд, выводим
        }


    public Optional<Recipe> findById(Integer id) {
        return repository.findById(id);
    }

    public void saveRecipe(Recipe recipe) {
        repository.save(recipe);
    }
}
