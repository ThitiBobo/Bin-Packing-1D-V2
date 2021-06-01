package models.operations;

import models.Item;

public class SwitchOperation extends Operation{

    private Item item1;
    private Item item2;

    public Item getItem1() {
        return item1;
    }

    private void setItem1(Item item1) {
        if (item1 == null) throw  new IllegalArgumentException("item1 is null");
        this.item1 = item1;
    }

    public Item getItem2() {
        return item2;
    }

    private void setItem2(Item item2) {
        if (item2 == null) throw  new IllegalArgumentException("item2 is null");
        this.item2 = item2;
    }

    public SwitchOperation(int _objectiveValue, Item _item1, Item _item2) {
        super(_objectiveValue);
        setItem1(_item1);
        setItem2(_item2);
    }

    @Override
    public boolean check() {
        if (item1.compareTo(item2) == 0) return false;
        if (item1.getBin() == item2.getBin()) return false;
        if (item1.getBin().getRemainingSpace() + item1.getSize() - item2.getSize() < 0) return false;
        if (item2.getBin().getRemainingSpace() + item2.getSize() - item1.getSize() < 0) return false;
        return true;
    }

    @Override
    public void updateObjectiveValue(int objectiveValue) {
        int newObjectiveValue1 = item1.getBin().getOccupiedSpace() - item1.getSize() + item2.getSize();
        int newObjectiveValue2 = item2.getBin().getOccupiedSpace() - item2.getSize() + item1.getSize();
        this.setObjectiveValue(
                (int)(objectiveValue
                        - item1.getBin().getObjectiveValue()
                        - item2.getBin().getObjectiveValue()
                        + Math.pow(newObjectiveValue1,2)
                        + Math.pow(newObjectiveValue2, 2))
        );
    }
}
