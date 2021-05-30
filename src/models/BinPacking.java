package models;

import jdk.jshell.spi.ExecutionControl;
import models.operations.MoveOperation;
import models.operations.Operation;
import models.operations.SwitchOperation;

import java.util.ArrayList;
import java.util.List;

public class BinPacking {

    private int objectiveValue;
    private int sizeLimit;
    private List<Bin> binList;
    private List<Item> itemList;

    public int getObjectiveValue(){ return objectiveValue; }
    public int getSizeLimit(){ return sizeLimit; }
    public List<Item> getItemList() { return itemList; }
    public List<Bin> getBinList() { return binList; }

    public int getTheoreticalMinimumBinNumber(){
        float sum = itemList.stream().mapToInt( i -> {return i.getSize();}).sum();
        return (int)Math.ceil(sum / sizeLimit);
    }

    public void setBinList(List<Bin> _list){
        if (_list == null) throw new IllegalArgumentException("list can't be null");
        this.objectiveValue = 0;
        this.binList = _list;
        this.binList.forEach(bin -> {
            this.objectiveValue += bin.getObjectiveValue();
            if (bin.getSizeLimit() != this.sizeLimit) throw new IllegalArgumentException("Any bin'sizeLimit must equal the given sizeLimit");
            bin.getList().forEach(item -> {
                item.setBin(bin);
            });
        });

        this.itemList.forEach(item -> {
            if (item.getBin() == null) throw new IllegalArgumentException("An item is not present in one of the bin");
        });
    }

    public void setItemList(List<Item> _list){
        if (_list == null) throw new IllegalArgumentException("list can't be null");
        this.itemList = _list;
        this.itemList.forEach( item -> {
            if (item.getSize() > this.sizeLimit) throw new IllegalArgumentException("Any item's size must equal or inferior the given sizeLimit");
        });
    }

    public int getSumItemSize(){
        int size = 0;
        for (Item item : this.itemList){
            size += item.getSize();
        }
        return size;
    }

    public BinPacking(int _sizeLimit, List<Item> _items, List<Bin> _list){
        this.sizeLimit = _sizeLimit;
        setItemList(_items);
        setBinList(_list);
    }

    public BinPacking(int _sizeLimit, List<Item> _items){
        this.sizeLimit = _sizeLimit;
        setItemList(_items);
    }

    private int updateObjectiveValue(){
        this.objectiveValue = 0;
        this.binList.forEach(bin -> {
            this.objectiveValue += bin.getObjectiveValue();
        });
        return this.objectiveValue;
    }

    public SwitchOperation switchItem(Item item1, Item item2) throws Exception {
        if (item1.getBin() == null || item2.getBin() == null)
            throw new Exception("items must be in a bin");
        // GET ALL BINs OBJECTIVE VALUES
        int oldObjValBin1 = item1.getBin().getObjectiveValue();
        int oldObjValBin2 = item2.getBin().getObjectiveValue();
        // REMOVE ITEMS
        item1.getBin().remove(item1);
        item2.getBin().remove(item2);
        // CHECK SPACE
        try{
            if (!item1.getBin().hasSpace(item2)) throw new Exception("Not enough space in the bin");
            if (!item2.getBin().hasSpace(item1)) throw new Exception("Not enough space in the bin");
        } catch (Exception e){
            // PUTS ITEMS BACK
            item1.getBin().add(item1);
            item2.getBin().add(item2);
            throw e;
        }
        // MOVE ITEMS
        item1.getBin().add(item2);
        item2.getBin().add(item1);
        // UPDATE ITEM'S BIN
        Bin bin2 = item2.getBin();
        item2.setBin(item1.getBin());
        item1.setBin(bin2);
        // UPDATE OBJECTIVE VALUE
        this.objectiveValue -= (oldObjValBin1 + oldObjValBin2);
        this.objectiveValue += (item1.getBin().getObjectiveValue() + item2.getBin().getObjectiveValue());
        return new SwitchOperation(this.objectiveValue, item1, item2);
    }

    public MoveOperation moveItem(Item item, Bin newBin) throws Exception {
        if (!newBin.hasSpace(item)) throw new Exception("Not enough space in the bin");
        if (item.getBin() == null) throw new Exception("item must be contained in a bin");
        if (!binList.contains(newBin)) binList.add(newBin);

        Bin oldBin = item.getBin();
        int oldObjectiveValue = oldBin.getObjectiveValue();

        // MOVE ITEM
        oldBin.remove(item);
        newBin.add(item);
        item.setBin(newBin);

        // IF OLD BIN EMPTY
        if (oldBin.isEmpty()) binList.remove(oldBin);

        // UPDATE OBJECTIVE VALUE
        this.objectiveValue += (newBin.getObjectiveValue() - oldObjectiveValue);
        return new MoveOperation(this.objectiveValue, item, newBin);
    }

    public List<Operation> getAllNeighborhoodOperation() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("fe");
    }

    public List<Operation> getAllSwitchOperation() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("fe");
    }

    public List<Operation> getAllMoveOperation() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("fe");
    }

    @Override
    public String toString() {
        return "BinPacking{" +
                "objectiveValue=" + objectiveValue +
                ", sizeLimit=" + sizeLimit +
                ", binList=" + binList +
                ", itemList=" + itemList +
                '}';
    }
}
