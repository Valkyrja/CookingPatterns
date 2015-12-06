package org.cookingpatterns.Interfaces;

import java.io.Serializable;

public interface ILiteralNode extends Serializable {
    void acceptVisitor(ISyntaxTreeVisitor visitor);
}
