package models.operations;

import models.Bin;
import models.Item;

public class MoveOperation extends Operation{

    private Item item;

    public Item getItem() {
        return item;
    }

    private void setItem(Item item) {
        if (item == null) throw  new IllegalArgumentException("item is null");
        this.item = item;
    }

    public Bin getBin() {
        return bin;
    }

    public void setBin(Bin bin) {
        if (bin == null) throw new IllegalArgumentException("bin is null");
        this.bin = bin;
    }

    private Bin bin;

    public MoveOperation(int _objectiveValue, Item _item, Bin _bin) {
        super(_objectiveValue);
        setItem(_item);
        setBin(_bin);
    }
}
