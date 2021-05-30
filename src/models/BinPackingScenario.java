package models;

import models.operations.Operation;

import java.util.ArrayList;
import java.util.List;

public class BinPackingScenario {

    private int sizeLimit;
    private List<Item> itemList;
    private BinPacking originalBinList;
    private BinPacking bestBinList;
    private BinPacking binPacking;
    private List<Operation> operationHistory;

    public List<Item> getItemList(){
        return this.itemList;
    }

    private void setItemList(List<Item> _items){
        this.itemList = _items;
    }

    public void setBinList(int sizeLimit, List<Bin> list){
        // TODO à améliorer
        this.binPacking = new BinPacking(sizeLimit, this.itemList, list);
    }

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
