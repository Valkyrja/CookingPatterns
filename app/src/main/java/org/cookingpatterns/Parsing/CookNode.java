package org.cookingpatterns.Parsing;

import org.cookingpatterns.Interfaces.ISyntaxTreeVisitor;

/**
 * Created by Andreas on 02.12.2015.
 */
public class CookNode extends Node {

    @Override
    public void acceptVisitor(ISyntaxTreeVisitor visitor) {
        visitor.VisitCookNode(this);
    }
}
