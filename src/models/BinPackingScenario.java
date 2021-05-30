package models;

import models.operations.Operation;

import java.util.List;

public class BinPackingScenario {

    private int sizeLimit;
    private List<Item> itemList;
    private BinPacking originalBinList;
    private BinPacking bestBinList;
    private BinPacking binList;
    private List<Operation> operationHistory;

    public int getSizeLimit() { return this.sizeLimit; }
    public List<Item> getItemList(){
        return this.itemList;
    }

    public BinPacking getBinList() { return this.binList; }

    private void setItemList(List<Item> _items){
        this.itemList = _items;
    }

    public void setBinList(int sizeLimit, List<Bin> list){
        // TODO à améliorer
        this.binList = new BinPacking(sizeLimit, this.itemList, list);
    }

    public int getObjectiveValue() { return binList.getObjectiveValue(); }
    public int getBestObjectiveValue() { return bestBinList.getObjectiveValue(); }
    public int getOriginalObjectiveValue() { return originalBinList.getObjectiveValue(); }

    public int getTheoreticalMinimumBinNumber(){
        float sum = itemList.stream().mapToInt( i -> {return i.getSize();}).sum();
        return (int)Math.ceil(sum / sizeLimit);
    }

    public BinPackingScenario(int sizeLimit, List<Item> itemList, List<Bin> binList){
        this.sizeLimit = sizeLimit;
        setItemList(itemList);
        setBinList(sizeLimit, binList);
    }

    public BinPackingScenario(int sizeLimit, List<Item> itemList){
        this.sizeLimit = sizeLimit;
        setItemList(itemList);
    }

}
