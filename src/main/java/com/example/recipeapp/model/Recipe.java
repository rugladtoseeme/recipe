package com.example.recipeapp.model;

import jakarta.persistence.*;

import java.util.ArrayList;



@Entity
@Table(name = "recipies")
public class Recipe {

    @Id
    private Integer id;

    @Column
    private String title;

    @Transient
    private String ingredients;

    @Transient
    private String method;

    @Column
    private String imageUrl;

    public Recipe(){}

    public Recipe(int id,
                  String imageUrl,
                  String method,
                  String ingredients,
                  String title) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.method = method;
        this.ingredients = ingredients;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
    

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", ingredients=" + ingredients +
                ", method=" + method +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
