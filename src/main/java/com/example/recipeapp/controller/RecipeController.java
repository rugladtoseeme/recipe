package com.example.recipeapp.controller;


import com.example.recipeapp.model.Recipe;
import com.example.recipeapp.service.RecipesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@ResponseBody
@RequestMapping("/api/recipes")
public class RecipeController {

    @Autowired
    private RecipesService service;

    @GetMapping("")
    public ArrayList<Recipe> findAllRecipes(){

        ArrayList<Recipe> recipeList = service.findAllRecipes();

        System.out.println(recipeList);

        return recipeList;

    }

    @GetMapping("/{id}")
    public Recipe findById(@PathVariable Integer id){

        Recipe recipe = service.getById(id);

        System.out.println(recipe);

        return recipe;
    }

    @PutMapping("/add")
    public void saveRecipe(@RequestBody Recipe recipe){

        service.saveRecipe(recipe);
    }


}
