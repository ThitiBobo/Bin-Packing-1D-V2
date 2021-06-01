package models.operations;

import models.Bin;
import models.BinPacking;
import models.Item;

public class MoveOperation extends Operation{

    private Item item;
    private Bin bin;
    private int binIndex;

    public Item getItem() {
        return item;
    }
    public Bin getBin() {
        return bin;
    }
    public int getBinIndex() { return binIndex; }

    private void setItem(Item item) {
        if (item == null) throw  new IllegalArgumentException("item is null");
        this.item = item;
    }

    public void setBin(Bin bin) {
        if (bin == null) throw new IllegalArgumentException("bin is null");
        this.bin = bin;
    }

    public MoveOperation(int _objectiveValue, Item _item, Bin _bin, int binIndex) {
        super(_objectiveValue);
        setItem(_item);
        setBin(_bin);
        this.binIndex = binIndex;
    }

    @Override
    public boolean check() {
        if (item.getBin() == bin) return false;
        return bin.hasSpace(item);
    }

    @Override
    public void updateObjectiveValue(int objectiveValue) {
        int oldBinOccupiedSpace = item.getBin().getOccupiedSpace() - item.getSize();
        int newBinOccupiedSpace = bin.getOccupiedSpace() + item.getSize();

        this.setObjectiveValue(
                (int) (objectiveValue
                    - item.getBin().getObjectiveValue()
                    - bin.getObjectiveValue()
                    + Math.pow(oldBinOccupiedSpace, 2)
                    + Math.pow(newBinOccupiedSpace, 2))
        );
    }
}
