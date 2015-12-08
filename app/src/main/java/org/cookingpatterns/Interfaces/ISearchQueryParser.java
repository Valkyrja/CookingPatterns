package org.cookingpatterns.Interfaces;

import org.cookingpatterns.Parsing.Node;

public interface ISearchQueryParser {
    Node ParseString(String searchQuery);
}
