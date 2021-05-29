package models.operations;

public abstract class Operation {

    private int objectiveValue;

    public int getObjectiveValue() { return this.objectiveValue; }

    private void setObjectiveValue(int value) {
        this.objectiveValue = value;
    }

    public Operation(int _objectiveValue){
        setObjectiveValue(_objectiveValue);
    }
}
