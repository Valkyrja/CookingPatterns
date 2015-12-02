package org.cookingpatterns.Parsing;

import org.cookingpatterns.Interfaces.ISyntaxTreeVisitor;

public class RootNode extends Node {


    @Override
    public void acceptVisitor(ISyntaxTreeVisitor visitor) {
        visitor.VisitRootNode(this);
    }
}
