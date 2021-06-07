package models;

import jdk.jshell.spi.ExecutionControl;
import models.operations.MoveOperation;
import models.operations.Operation;
import models.operations.SwitchOperation;
import utils.BinPackingUtils;
import utils.CombinationGenerator;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    public void switchItem(SwitchOperation operation) throws Exception {
        Item item1 = operation.getItem1();
        Item item2 = operation.getItem2();
        if (item1.getBin() == null || item2.getBin() == null)
            throw new Exception("items must be in a bin");
        // CHECK SPACE
        if (!operation.check()) throw new Exception("operation not valid");
        // REMOVE ITEMS
        item1.getBin().remove(item1);
        item2.getBin().remove(item2);
        // MOVE ITEMS
        item1.getBin().add(item2);
        item2.getBin().add(item1);
        // UPDATE ITEM'S BIN
        Bin bin2 = item2.getBin();
        item2.setBin(item1.getBin());
        item1.setBin(bin2);
        // UPDATE OBJECTIVE VALUE
        this.objectiveValue = operation.getObjectiveValue();
    }

    public void moveItem(MoveOperation operation) throws Exception {
        Item item = operation.getItem();
        Bin newBin = operation.getBin();
        if (!operation.check()) throw new Exception("Not enough space in the bin");
        if (item.getBin() == null) throw new Exception("item must be contained in a bin");
        if (!binList.contains(newBin)) binList.add(newBin);
        Bin oldBin = item.getBin();
        // MOVE ITEM
        oldBin.remove(item);
        newBin.add(item);
        item.setBin(newBin);
        // IF OLD BIN EMPTY
        if (oldBin.isEmpty()) binList.remove(oldBin);
        // UPDATE OBJECTIVE VALUE
        this.objectiveValue = operation.getObjectiveValue();
    }

    public List<Operation> getAllNeighborhoodOperation(){
        List<Operation> operations = new ArrayList<>();
        operations.addAll(getAllSwitchOperation());
        operations.addAll(getAllMoveOperation());
        return operations;
    }

    private List<Operation> getAllSwitchOperation() {
        List<Pair<Item, Item>> combinations = CombinationGenerator.combination(itemList);
        List<Operation> validOperations = new ArrayList<>();

        combinations.forEach( couple -> {
            SwitchOperation op = new SwitchOperation(0, couple.getL(), couple.getR());
            if(op.check()){
                op.updateObjectiveValue(objectiveValue);
                op.calculateHash(this);
                validOperations.add(op);
            }
        });
        return validOperations;
    }

    private List<Operation> getAllMoveOperation() {

        List<Bin> bins = new ArrayList<>(this.binList);
        bins.add(new Bin(sizeLimit));
        List<Pair<Item, Bin>> combinations = CombinationGenerator.combination(itemList, bins);
        List<Operation> validOperations = new ArrayList<>();

        combinations.forEach( couple -> {
            MoveOperation op = new MoveOperation(0, couple.getL(), couple.getR(), 0);
            if (op.check()){
                op.updateObjectiveValue(objectiveValue);
                op.calculateHash(this);
                validOperations.add(op);
            }
        });
        return validOperations;
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
                op = new MoveOperation(0, item, bin, binIndex);
            }
            // CHECK NEIGHBORHOOD
        } while (!isValid(op));
        // UPDATE OPERATION'S OBJECTIVE VALUE
        op.updateObjectiveValue(this.getObjectiveValue());

        // CALCUL HASH
        op.calculateHash(this);

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

    public String hash() {
        StringBuilder builder = new StringBuilder();
        builder.append("B");
        this.binList.forEach( bin -> {
            bin.getList().forEach( item -> {
                builder.append(item.getSize());
            });
        });
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(builder.toString().getBytes());
            byte byteData[] = md.digest();

            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
