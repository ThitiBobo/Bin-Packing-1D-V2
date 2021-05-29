package models;

import java.util.ArrayList;
import java.util.List;

public class Bin {

    private int sizeLimit;
    private int objectiveValue;
    private List<Item> list;
    private int occupiedSpace;

    public int getSizeLimit(){
        return sizeLimit;
    }
    public int getObjectiveValue() {
        return objectiveValue;
    }
    public List<Item> getList() {
        return list;
    }
    private void setList(List<Item> _item){
        if (_item == null) throw new IllegalArgumentException("list can't be null");
        this.list.forEach( item -> {
            this.occupiedSpace += item.getSize();
        });
        if (this.occupiedSpace > this.sizeLimit) throw new IllegalArgumentException("to much items for the bin");
        this.objectiveValue = this.occupiedSpace * this.occupiedSpace;
    }

    public Bin(int _sizeLimit, List<Item> _list){
        this.sizeLimit = _sizeLimit;
        setList(_list);
    }

    public Bin(int _sizeLimit){
        this(_sizeLimit, new ArrayList<>());
    }

    public boolean isFull(){

    }

    public boolean isEmpty(){

    }

    public boolean hasSpace(Item _item){

    }

}
