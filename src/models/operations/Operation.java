package models.operations;

import models.BinPacking;

public abstract class Operation {

    protected int objectiveValue;
    protected String hash;

    public int getObjectiveValue() { return this.objectiveValue; }
    public String getHash() {return hash;};

    protected void setObjectiveValue(int value) {
        this.objectiveValue = value;
    }

    public void setHash(String _hash){
        this.hash = _hash;
    }

    public Operation(int _objectiveValue){
        setObjectiveValue(_objectiveValue);
    }

    public abstract boolean check();

    public abstract void updateObjectiveValue(int objectiveValue);
}
