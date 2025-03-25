package com.example.recipeapp.repository;

import com.example.recipeapp.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    List<Ingredient> findAllByDescriptionIn(Collection<String> descriptions);
}