package org.cookingpatterns.Model;

import java.util.List;
import java.util.UUID;
import java.util.UnknownFormatConversionException;

/**
 * Created by Andreas on 10.11.2015.
 */
public class Recipe {

    private final UUID Id;
    private String Name;
    private String Category;
    private Integer Portions;
    private String Time;
    private ImageInfo Image;
    public Integer Rating;

    private List<Ingredient> ingredients; //TODO replace with other datastructure

    private String Description;

    //TODO add all other things

    public Recipe() { Id = UUID.randomUUID(); }

    public Recipe(UUID id)
    {
        Id = id;
    }

    /*public Recipe(UUID id, String name, String category, Integer portions, String time, Integer rating)
    {
        Id = id;
        Name;
        Category;
        Portions;
        Time;
        Image;
        Rating;
    }*/

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public String getCategory() { return Category; }
    public void setCategory(String category) { Category = category; }

    public Integer getPortions() { return Portions; }
    public void setPortions(Integer portions) { Portions = portions; }

    public String getTime() { return Time; }
    public void setTime(String time) { Time = time; }

    public ImageInfo getImage() {
        return Image;
    }
    public void setImage(ImageInfo image) {
        Image = image;
    }

    public Integer getRating() {
        return Rating;
    }
    public void setRating(Integer rating) {
        Rating = rating;
    }

    public String getDescription() {
        return Description;
    }
    public void setDescription(String description) {
        Description = description;
    }

    public UUID getId() {
        return Id;
    }
}
