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



        //I want to cook 123 ,for 3, I have 1 2 3

        String[] parts = searchQuery.split(",");

        for (String part : parts) {

            if (part.startsWith("I want to cook")) {
                String a = part.substring(13);
                ILiteralNode textNode = new TextNode(a);


            } else if (part.startsWith("for")) {

            } else if (part.startsWith("I have")) {
                String a = part.substring(6);
                String[] ingreedientstrings = a.split(" ");

                for (String ingreedientstring : ingreedientstrings) {

                    String[] b = ingreedientstring.split("\\(");

                    Ingredient ingredient = new Ingredient();
                    ingredient.setName(b[0].trim());

                    if (b.length == 2) {
                        ingredient.setAmount(Double.parseDouble(StringUtils.strip(b[1], ")")));
                    }

                    ILiteralNode node = new IngredientNode(ingredient);


                }

            }


        }

        return null;
    }
}
