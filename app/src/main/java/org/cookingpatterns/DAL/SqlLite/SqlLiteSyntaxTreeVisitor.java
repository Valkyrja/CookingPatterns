package org.cookingpatterns.DAL.SqlLite;

import org.cookingpatterns.Interfaces.ISyntaxTreeVisitor;
import org.cookingpatterns.Model.Ingredient;
import org.cookingpatterns.Parsing.AndNode;
import org.cookingpatterns.Parsing.CookNode;
import org.cookingpatterns.Parsing.HaveNode;
import org.cookingpatterns.Parsing.IngredientNode;
import org.cookingpatterns.Parsing.OrNode;
import org.cookingpatterns.Parsing.RootNode;
import org.cookingpatterns.Parsing.TextNode;

import java.util.LinkedList;
import java.util.Stack;

/**
 * Created by Andreas on 25.11.2015.
 */
public class SqlLiteSyntaxTreeVisitor implements ISyntaxTreeVisitor {

    private String sql;
    private LinkedList<String> sqlParameter;
    private int state; //TODO replace with enum
    private Stack<String> _operatorStack;


    public SqlLiteSyntaxTreeVisitor() {
        sql = "";
        sqlParameter = new LinkedList<>();
        state = 0;
        _operatorStack = new Stack<>();
    }

    public String GetSql() {
        SwitchState(99);

        return sql;
    }

    public LinkedList<String> GetSqlParameter() {
        return sqlParameter;
    }

    private void SwitchState(int newState) {
        switch (state) {
            case 1:
                break;
            case 2:
                break;
        }
        sql += ") ";

        state = newState;
    }

    @Override
    public void VisitRootNode(RootNode node) {

        for (int i = 0; i < node.childes.size() - 1; i++) {
            _operatorStack.push("AND ");
        }
    }

    @Override
    public void VisitCookNode(CookNode node) {
        SwitchState(1);
        sql += "( ";
    }

    @Override
    public void VisitAndNode(AndNode node) {

        _operatorStack.push("AND ");
    }

    @Override
    public void VisitOrNode(OrNode node) {
        _operatorStack.push("OR ");
    }

    @Override
    public void VisitHaveNode(HaveNode node) {

        SwitchState(2);
        sql += " 1 > COUNT(SELECT * FROM " + IngredientEntry.TABLE_NAME +
                " AS i INNER JOIN " + RecipeIngredientEntry.TABLE_NAME +
                " AS ri ON ri." + RecipeIngredientEntry.COLUMN_INGREDIENTID.Name +
                " = i." + IngredientEntry.COLUMN_ID.Name +
                " WHERE ri." + RecipeIngredientEntry.COLUMN_RECIPEID.Name +
                " = r." + RecipeEntry.COLUMN_ID.Name + " ";
    }

    @Override
    public void VisitTextNode(TextNode node) {

        sql += RecipeEntry.COLUMN_NAME.Name + " LIKE ? ";
        sqlParameter.push(node.getText());

        if (!_operatorStack.isEmpty()) {
            sql += _operatorStack.pop();
        }
    }

    @Override
    public void VisitIngredientNode(IngredientNode node) {

        sql += "AND (i." + IngredientEntry.COLUMN_NAME.Name + "  <>  ? OR (i." +
                IngredientEntry.COLUMN_NAME.Name + "  =  ? AND ri." +
                RecipeIngredientEntry.COLUMN_AMOUNT.Name + " < ? )) ";

        sqlParameter.push(node.getIngredient().getName());
        sqlParameter.push(node.getIngredient().getName());
        sqlParameter.push(Double.toString(node.getIngredient().getAmount()));
    }
}
