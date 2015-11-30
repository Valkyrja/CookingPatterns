package org.cookingpatterns.Parsing;

import org.cookingpatterns.Interfaces.ISyntaxTreeVisitor;

/**
 * Created by Andreas on 25.11.2015.
 */
public class TextNode extends Node {

    private String _test;

    public TextNode(String text) {
        _test = text;
    }

    public String getText() {
        return _test;
    }

    @Override
    public void acceptVisitor(ISyntaxTreeVisitor visitor) {

        visitor.VisitTextNode(this);
    }
}
