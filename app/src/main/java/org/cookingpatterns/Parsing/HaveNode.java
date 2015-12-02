package org.cookingpatterns.Parsing;

import org.cookingpatterns.Interfaces.ISyntaxTreeVisitor;

/**
 * Created by Andreas on 25.11.2015.
 */
public class HaveNode extends Node {


    @Override
    public void acceptVisitor(ISyntaxTreeVisitor visitor) {
        visitor.VisitHaveNode(this);
    }
}
