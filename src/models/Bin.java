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
    public int getOccupiedSpace() { return this.occupiedSpace; }
    private void setList(List<Item> _item){
        if (_item == null) throw new IllegalArgumentException("list can't be null");
        this.occupiedSpace = 0;
        this.list = _item;
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
        return this.occupiedSpace >= this.sizeLimit;
    }

    public boolean isEmpty(){
        return this.list.isEmpty();
    }

    public boolean hasSpace(Item _item){
        return _item.getSize() <= (this.sizeLimit - this.occupiedSpace);
    }

    public void add(Item _item) throws Exception {
        if (!hasSpace(_item)) throw new Exception("not enough space to put the item");
        this.list.add(_item);
        this.occupiedSpace += _item.getSize();
        this.updateObjectiveValue();
    }

    public Item remove(int _index){
        if (_index < 0 || _index >= this.list.size()) throw new IllegalArgumentException("index must be in bornes");
        Item item = this.list.remove(_index);
        this.occupiedSpace -= item.getSize();
        this.updateObjectiveValue();
        return item;
    }

    public Item remove(Item item){
        return remove(list.indexOf(item));
    }

    private int updateObjectiveValue(){
        this.objectiveValue = (int)Math.pow(this.occupiedSpace, 2);
        return this.objectiveValue;
    }

    @Override
    public String toString() {
        return "Bin{" +
                "sizeLimit=" + sizeLimit +
                ", objectiveValue=" + objectiveValue +
                ", list=" + list +
                ", occupiedSpace=" + occupiedSpace +
                '}';
    }
}
