package models;

import models.operations.Operation;
import utils.BinPackingUtils;

import java.util.ArrayList;
import java.util.List;

public class BinPackingScenario implements Comparable<BinPackingScenario>{

    private int sizeLimit;
    private String name;
    private List<Item> itemList;
    private BinPacking originalBinList;
    private BinPacking bestBinList;
    private BinPacking binList;
    private List<Operation> operationHistory;

    public int getSizeLimit() { return this.sizeLimit; }
    public String getName() {return this.name; }
    public List<Item> getItemList(){
        return this.itemList;
    }

    public BinPacking getBinList() { return this.binList; }
    public BinPacking getBestBinList() { return this.bestBinList; }
    public BinPacking getOriginalBinList() { return this.originalBinList; }

    public int getObjectiveValue() { return binList.getObjectiveValue(); }
    public int getBestObjectiveValue() { return bestBinList.getObjectiveValue(); }
    public int getOriginalObjectiveValue() { return originalBinList.getObjectiveValue(); }

    public List<Operation> getOperationHistory() { return this.operationHistory; }

    public int getTheoreticalMinimumBinNumber(){
        float sum = itemList.stream().mapToInt( i -> {return i.getSize();}).sum();
        return (int)Math.ceil(sum / sizeLimit);
    }

    private void setItemList(List<Item> _items){
        this.itemList = _items;
    }

    public void setBinList(int sizeLimit, List<Bin> list){
        // TODO à améliorer
        this.binList = new BinPacking(sizeLimit, this.itemList, list);
        this.originalBinList = (BinPacking) this.binList.clone();
        this.bestBinList = (BinPacking) this.binList.clone();
    }

    public BinPackingScenario(String name, int sizeLimit, List<Item> itemList, List<Bin> binList){
        this.name = name;
        this.sizeLimit = sizeLimit;
        this.operationHistory = new ArrayList<>();
        setItemList(itemList);
        setBinList(sizeLimit, binList);
    }

    public BinPackingScenario(String name, int sizeLimit, List<Item> itemList){
        this.name = name;
        this.sizeLimit = sizeLimit;
        this.operationHistory = new ArrayList<>();
        setItemList(itemList);
    }

    public Operation getRandomOperation() {
        return this.binList.getRandomOperation();
    }

    public void updateHistory(Operation op){
        this.operationHistory.add(op);
    }

    public void updateBestScenario() {
        this.bestBinList = (BinPacking) this.binList.clone();
    }

    @Override
    public int compareTo(BinPackingScenario o) {
        return this.name.compareTo(o.getName());
    }
}
