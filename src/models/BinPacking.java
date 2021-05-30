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

    public SwitchOperation switchItem() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("fe");
    }

    public MoveOperation moveItem() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("fe");
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
