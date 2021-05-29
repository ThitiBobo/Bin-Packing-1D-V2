package models;

/**
 * The item class represents 1D size objects to store in the Bin
 */
public class Item {

    private int id;
    private int size;
    private Bin bin;

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public int getSize() {
        return size;
    }

    private void setSize(int size) {
        this.size = size;
    }

    public Bin getBin() {
        return bin;
    }

    public void setBin(Bin bin) {
        this.bin = bin;
    }

    public Item(int _id, int _size){
        setId(_id);
        setSize(_size);
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", size=" + size +
                ", bin=" + bin +
                '}';
    }
}
