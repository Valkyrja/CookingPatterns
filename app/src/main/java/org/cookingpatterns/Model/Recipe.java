package org.cookingpatterns.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.UnknownFormatConversionException;

/**
 * Created by Andreas on 10.11.2015.
 */
public class Recipe implements Serializable
{
    // For Serializable fields need to be public (sorry)
    public UUID Id;
    public String Name;
    public String Category;
    public Integer Portions;
    public String Time;
    public ImageInfo Image;
    public Integer Rating;

    public List<Ingredient> ingredients;

    public String Description;

    //TODO add all other things

    public Recipe()
    {
        this(UUID.randomUUID());
    }

    public Recipe(UUID id)

    {
        Id = id;
        ingredients = new ArrayList<Ingredient>();
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
        Name = name == null ? "" : name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }
    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }

    public String getCategory() { return Category; }
    public void setCategory(String category) { Category = category == null ? "" : category; }

    public Integer getPortions() { return Portions; }
    public String getPortionsAsString() { return Portions == null ? "0" : Portions.toString(); }
    public void setPortions(Integer portions) { Portions = portions == null ? 0 : portions; }

    public String getTime() { return Time; }
    public void setTime(String time) { Time = time == null ? "" : time; }

    public ImageInfo getImage() {
        return Image;
    }
    public void setImage(ImageInfo image) {
        Image = image;
    }

    public Integer getRating() {
        return Rating;
    }
    public int getRatingNotNullable() { return Rating == null ? 0 : Rating; }
    public void setRating(Integer rating) {
        Rating = rating == null ? 0 : rating;
    }

    public String getDescription() {
        return Description;
    }
    public void setDescription(String description) { Description = description == null ? "" : description; }

    public UUID getId() {
        return Id;
    }
}
