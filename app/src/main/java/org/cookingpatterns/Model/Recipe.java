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
    private List<Ingredient> ingredients; //TODO replace with other datastructure

    //TODO add all other things

    public Recipe()
    {
        Id = UUID.randomUUID();
    }

    public Recipe(UUID id)
    {
        Id = id;
    }

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

    public UUID getId() {
        return Id;
    }
}
