package org.cookingpatterns.Interfaces;


import org.cookingpatterns.Parsing.RootNode;

public interface ISyntaxTreeVisitor
{
    void VisitRootNode(RootNode node);
}
