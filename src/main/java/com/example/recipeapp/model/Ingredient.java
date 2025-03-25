package com.example.recipeapp.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ingredients")
        // uniqueConstraints = @UniqueConstraint(columnNames = "description"))
public class Ingredient implements Serializable {

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="recipes_ingredients",
            joinColumns=  @JoinColumn(name="ingredients_id", referencedColumnName="id"),
            inverseJoinColumns= @JoinColumn(name="recipe_id", referencedColumnName="id") )
   // @JoinColumn(name = "recipe_id")
    private Set<Recipe> recipeSet = new HashSet<Recipe>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String quantity;

    private String unitOfMeasure;

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public Ingredient() {}

    @JsonCreator
    public Ingredient(@JsonProperty("quantity") String quantity,
                      @JsonProperty("unitOfMeasure") String unitOfMeasure,
                      @JsonProperty("description") String description) {
        this.quantity = quantity;
        this.unitOfMeasure = unitOfMeasure;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "quantity=" + quantity +
                ", unitOfMeasure='" + unitOfMeasure + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
