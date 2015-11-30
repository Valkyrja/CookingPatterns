package org.cookingpatterns.Parsing;

import org.cookingpatterns.Interfaces.ILiteralNode;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by Andreas on 30.11.2015.
 */
public abstract class Node implements ILiteralNode, Iterable<Node> {

    public LinkedList<Node> childes;
    public Node parent;

    public boolean hasChildren() {
        if (childes != null && !childes.isEmpty()) {
            return true;
        }

        return false;
    }

    public void addChild(Node child) {
        child.parent = this;
        childes.add(child);
    }

    @Override
    public Iterator<Node> iterator() {
        return new TreeIterator(this);
    }
}
