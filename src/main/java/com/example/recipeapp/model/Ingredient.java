package com.example.recipeapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;

@Entity
public class Ingredient implements Serializable {

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @Id
    private Integer id;

    private int quantity;
    private String unitOfMeasure;
    private String description;
}
