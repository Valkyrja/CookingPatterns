package org.cookingpatterns.Parsing;

import org.cookingpatterns.Interfaces.ISyntaxTreeVisitor;

/**
 * Created by Andreas on 02.12.2015.
 */
public class OrNode extends Node {

    OrNode(Node leftNode, Node rightNode) {
        addChild(leftNode);
        addChild(rightNode);
    }

    public Node getLeftNode() {
        return childes.get(0);
    }

    public Node getRightNode() {
        return childes.get(1);
    }

    @Override
    public void acceptVisitor(ISyntaxTreeVisitor visitor) {

        visitor.VisitOrNode(this);
    }
}