package models;

import jdk.jshell.spi.ExecutionControl;
import models.operations.MoveOperation;
import models.operations.Operation;
import models.operations.SwitchOperation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BinPacking implements Cloneable{

    private int objectiveValue;
    private int sizeLimit;
    private List<Bin> binList;
    private List<Item> itemList;

    public int getObjectiveValue(){ return objectiveValue; }
    public int getSizeLimit(){ return sizeLimit; }
    public List<Item> getItemList() { return itemList; }
    public List<Bin> getBinList() { return binList; }

    public int getTheoreticalMinimumBinNumber(){
        return (int)Math.ceil((float)getSumItemSize() / (float)sizeLimit);
    }

    public int getSumItemSize(){
        return itemList.stream().mapToInt( i -> {return i.getSize();}).sum();
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

    private void setItemList(List<Item> _list){
        if (_list == null) throw new IllegalArgumentException("list can't be null");
        this.itemList = _list;
        this.itemList.forEach( item -> {
            if (item.getSize() > this.sizeLimit) throw new IllegalArgumentException("Any item's size must equal or inferior the given sizeLimit");
        });
    }

    public BinPacking(int _sizeLimit, List<Item> _items, List<Bin> _list){
        this.objectiveValue = 0;
        this.sizeLimit = _sizeLimit;
        setItemList(_items);
        setBinList(_list);
    }

    public BinPacking(int _sizeLimit, List<Item> _items){
        this.objectiveValue = 0;
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

    // TODO à améliorer ??
    public SwitchOperation switchItem(Item item1, Item item2) throws Exception {
        // TODO à amélioré ??
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

    // TODO à améliorer ??
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

    // TODO à implémenter
    public List<Operation> getAllNeighborhoodOperation() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("fe");
    }

    // TODO à implémenter
    public List<Operation> getAllSwitchOperation() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("fe");
    }

    // TODO à implémenter
    public List<Operation> getAllMoveOperation() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("fe");
    }

    public boolean isValid(Operation op){
        return op.check();
    }

    public Operation getRandomOperation() {
        Operation op;
        Random random = new Random();
        // while NEIGHBORHOOD is not CORRECT
        do{
            // GENERATE RANDOM NEIGHBORHOOD
            if ( (random.nextInt(2)) == 0){
                Item item1 = this.itemList.get(random.nextInt(this.itemList.size()));
                Item item2 = this.itemList.get(random.nextInt(this.itemList.size()));
                op = new SwitchOperation(0, item1, item2);
            }
            else {
                Item item = this.itemList.get(random.nextInt(this.itemList.size()));
                Bin bin;
                int binIndex = random.nextInt(this.binList.size() + 1);
                if (binIndex == this.binList.size()){ bin = new Bin(this.sizeLimit); }
                else { bin = this.binList.get(binIndex); }
                op = new MoveOperation(0, item, bin);
            }
            // CHECK NEIGHBORHOOD
        } while (!isValid(op));
        // UPDATE OPERATION'S OBJECTIVE VALUE
        op.updateObjectiveValue(this.getObjectiveValue());
        return op;
    }

    @Override
    protected Object clone() {
        List<Bin> list = new ArrayList<>();
        try {
            super.clone();
            this.binList.forEach( bin -> {
                try {
                    list.add((Bin)bin.clone());
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            });
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return new BinPacking(this.sizeLimit, this.itemList, list);
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
