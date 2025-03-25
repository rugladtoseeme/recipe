package com.example.recipeapp.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.recipeapp.model.Recipe;

import java.util.ArrayList;
import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Integer>{


    public ArrayList<Recipe> findAll();

    @EntityGraph(attributePaths = {"ingredients"})
    public Optional<Recipe> findById(int id);

}
