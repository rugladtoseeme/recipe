package com.example.recipeapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.recipeapp.model.Recipe;

import java.util.ArrayList;

public interface RecipeRepository extends JpaRepository<Recipe, Integer>{


    public ArrayList<Recipe> findAll();
    public Recipe findRecipeById(int id);

}
