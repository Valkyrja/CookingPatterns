package org.cookingpatterns.Parsing;

import org.cookingpatterns.Interfaces.ISyntaxTreeVisitor;

/**
 * Created by Andreas on 25.11.2015.
 */
public class AndNode extends Node {


    AndNode(Node leftNode, Node rightNode) {
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

        visitor.VisitAndNode(this);
    }
}
