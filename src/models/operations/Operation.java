package models.operations;

import models.BinPacking;

public abstract class Operation {

    protected int objectiveValue;

    public int getObjectiveValue() { return this.objectiveValue; }

    protected void setObjectiveValue(int value) {
        this.objectiveValue = value;
    }

    public Operation(int _objectiveValue){
        setObjectiveValue(_objectiveValue);
    }

    public abstract boolean check();

    public abstract void updateObjectiveValue(int objectiveValue);
}
