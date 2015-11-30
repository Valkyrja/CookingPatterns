package org.cookingpatterns.DAL.SqlLite;

import org.cookingpatterns.Interfaces.ISyntaxTreeVisitor;
import org.cookingpatterns.Parsing.AndNode;
import org.cookingpatterns.Parsing.HaveNode;
import org.cookingpatterns.Parsing.IngredientNode;
import org.cookingpatterns.Parsing.RootNode;
import org.cookingpatterns.Parsing.TextNode;

import java.util.LinkedList;

/**
 * Created by Andreas on 25.11.2015.
 */
public class SqlLiteSyntaxTreeVisitor implements ISyntaxTreeVisitor {

    private String sql;
    private LinkedList<String> sqlParameter;
    private int state;


    public SqlLiteSyntaxTreeVisitor() {
        sql = "";
        sqlParameter = new LinkedList<>();
        state = 0;
    }

    @Override
    public void VisitRootNode(RootNode node) {

    }

    @Override
    public void VisitAndNode(AndNode node) {

    }

    @Override
    public void VisitHaveNode(HaveNode node) {

        state = 2;

    }

    @Override
    public void VisitTextNode(TextNode node) {


    }

    @Override
    public void VisitIngredientNode(IngredientNode node) {

        sql += "";

    }
}
