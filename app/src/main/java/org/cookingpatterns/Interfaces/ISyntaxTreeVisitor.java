package org.cookingpatterns.Interfaces;

import org.cookingpatterns.Parsing.AndNode;
import org.cookingpatterns.Parsing.CookNode;
import org.cookingpatterns.Parsing.HaveNode;
import org.cookingpatterns.Parsing.IngredientNode;
import org.cookingpatterns.Parsing.OrNode;
import org.cookingpatterns.Parsing.RootNode;
import org.cookingpatterns.Parsing.TextNode;

public interface ISyntaxTreeVisitor {

    void VisitRootNode(RootNode node);

    void VisitAndNode(AndNode node);

    void VisitOrNode(OrNode node);

    void VisitHaveNode(HaveNode node);

    void VisitTextNode(TextNode node);

    void VisitIngredientNode(IngredientNode node);

    void VisitCookNode(CookNode node);
}
