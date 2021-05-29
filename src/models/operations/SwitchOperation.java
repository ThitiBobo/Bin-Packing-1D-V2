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
}
