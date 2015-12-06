package org.cookingpatterns.Parsing;

import org.cookingpatterns.Interfaces.ISyntaxTreeVisitor;
import org.cookingpatterns.Model.Ingredient;

/**
 * Created by Andreas on 27.11.2015.
 */
public class IngredientNode extends Node {

    public Ingredient _ingredient;

    public IngredientNode(Ingredient ingredient) {
        _ingredient = ingredient;
    }

    public Ingredient getIngredient() {
        return _ingredient;
    }

    @Override
    public void acceptVisitor(ISyntaxTreeVisitor visitor) {
        visitor.VisitIngredientNode(this);
    }
}
