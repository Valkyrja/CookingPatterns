package org.cookingpatterns.Model;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Andreas on 10.11.2015.
 */
public class Ingredient implements Serializable
{
    public UUID Id;
    public String Name;
    public double Amount;
    public UnitOfMeasure Unit;

    public Ingredient()
    {
        this(UUID.randomUUID());
    }

    public Ingredient(UUID id)
    {
        Id = id;
        Name = "";
        Amount = 0;
        Unit = UnitOfMeasure.pcs;
    }

    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }

    public double getAmount() { return Amount; }
    public void setAmount(double amount) {
        Amount = amount;
    }

    public UnitOfMeasure getUnit() {
        return Unit;
    }
    public void setUnit(UnitOfMeasure unit) {
        Unit = unit;
    }

    public UUID getId() {
        return Id;
    }

    @Override
    public String toString() {
        return getName();
    }
    //TODO add more
}
