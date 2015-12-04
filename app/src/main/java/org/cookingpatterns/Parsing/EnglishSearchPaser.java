package org.cookingpatterns.Parsing;

import org.apache.commons.lang.StringUtils;
import org.cookingpatterns.Interfaces.ILiteralNode;
import org.cookingpatterns.Interfaces.ISearchQueryParser;
import org.cookingpatterns.Model.Ingredient;

/**
 * Created by Andreas on 25.11.2015.
 */
public class EnglishSearchPaser implements ISearchQueryParser {
    @Override
    public ILiteralNode ParseString(String searchQuery) {

        //I want to cook kaiserschmarrn ,for 3, I have Ei(4) Mehl(500) Milch(500) Salz

        //example tree
        RootNode rootNode = new RootNode();

        // create cookNode
        CookNode cookNode = new CookNode();
        rootNode.addChild(cookNode);

        TextNode textNode = new TextNode("kaiserschmarrn");
        cookNode.addChild(textNode);

        // create Ihave Node
        HaveNode haveNode = new HaveNode();
        rootNode.addChild(haveNode);

        Ingredient ingredient = new Ingredient();
        ingredient.setName("Ei");
        ingredient.setAmount(4);
        IngredientNode ingredientNode = new IngredientNode(ingredient);
        haveNode.addChild(ingredientNode);

        ingredient = new Ingredient();
        ingredient.setName("Mehl");
        ingredient.setAmount(500);
        ingredientNode = new IngredientNode(ingredient);
        haveNode.addChild(ingredientNode);

        ingredient = new Ingredient();
        ingredient.setName("Milch");
        ingredient.setAmount(500);
        ingredientNode = new IngredientNode(ingredient);
        haveNode.addChild(ingredientNode);

        ingredient = new Ingredient();
        ingredient.setName("Salz");
        ingredientNode = new IngredientNode(ingredient);
        haveNode.addChild(ingredientNode);


        String[] parts = searchQuery.split("\\s*,\\s*"); //split by comma and remove whitespaces
        RootNode root = new RootNode();

        for (String part : parts) {

            if (part.startsWith("I want to cook")) {

                CookNode iWantToCook = new CookNode();
                root.addChild(iWantToCook);

                String a = part.substring(13);
                TextNode textNode1 = new TextNode(a);
                iWantToCook.addChild(textNode1);

            } else if (part.startsWith("for")) {
                //ignore this... not supported now!
            } else if (part.startsWith("I have")) {
                HaveNode ihave = new HaveNode();
                root.addChild(ihave);

                String a = part.substring(6);
                String[] ingreedientstrings = a.split("\\s+"); //split by whitespace

                for (String ingreedientstring : ingreedientstrings) {

                    String[] b = ingreedientstring.split("\\(");

                    Ingredient ingredient1 = new Ingredient();
                    ingredient1.setName(b[0].trim());

                    if (b.length == 2) {
                        ingredient1.setAmount(Double.parseDouble(StringUtils.strip(b[1], ")")));
                    }

                    IngredientNode node = new IngredientNode(ingredient1);
                    ihave.addChild(node);
                }

                if (ihave.hasChildren()) {
                    root.addChild(ihave);
                }

            }
            
        }

        return root.hasChildren() ? root : rootNode; // only for testing remove example tree later
    }
}
