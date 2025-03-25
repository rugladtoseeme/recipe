package com.example.recipeapp.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.*;


@Entity
@Table(name = "recipies")
public class Recipe {

    @Id
    private Integer id;

    @Column
    private String title;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "recipes_ingredients",
            joinColumns = @JoinColumn(name = "recipe_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "ingredients_id", referencedColumnName = "id"))
    private Set<Ingredient> ingredients = new HashSet<>();


    //@JsonSerialize(using = StringToListSerializer.class)
    @Column(length = 1000)
    private String method;

    @Column
    private String imageUrl;

    public Recipe() {
    }

    @JsonCreator
    public Recipe(@JsonProperty("id") int id,
                  @JsonProperty("imageUrl") String imageUrl,
                  @JsonProperty("method") List<String> method,
                  @JsonProperty("ingredients") Set<Ingredient> ingredients,
                  @JsonProperty("title") String title) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.method = String.join(", ", method);
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

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }


    public String[] getMethod() {
       return method.split(", ");
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    //public String getMethod() {
    //    return method;
    //}

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
                ", ingredients=" + ingredients.toString() +
                ", method=" + method +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

/*    class StringToListSerializer extends JsonSerializer<String> {

        public StringToListSerializer() {}

        @Override
        public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {

            //Set<String> ingredients = new HashSet<>();
            // = value.split(", ").
            String[] items = value.split(", ");

            gen.writeArray(items, 0, items.length);
        }
    }*/
}
