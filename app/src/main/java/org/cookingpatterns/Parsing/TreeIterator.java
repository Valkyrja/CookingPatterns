package org.cookingpatterns.Parsing;

import java.util.Iterator;

/**
 * Created by Andreas on 30.11.2015.
 */
public class TreeIterator implements Iterator<Node> {

    private Node _root;
    private Node _next;

    public TreeIterator(Node root) {
        _root = root;
        _next = _root;

    }

    @Override
    public boolean hasNext() {
        return _next != null;
    }

    @Override
    public Node next() {
        Node current = _next;
        Node lastTemp = null;
        Node temp = current;
        _next = null;

        while (_next == null) {
            if (temp.hasChildren()) {
                // set first child
                if (lastTemp == null) {
                    _next = temp.childes.getFirst();
                } else {
                    int lastindex = temp.childes.indexOf(lastTemp);
                    if (temp.childes.size() >= lastindex + 1) {
                        _next = temp.childes.get(lastindex + 1);
                        continue;
                    }
                }
            }

            if (temp != _root) {
                lastTemp = temp;
                temp = temp.parent;
            } else {
                //root
                break;
            }
        }

        return current;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
