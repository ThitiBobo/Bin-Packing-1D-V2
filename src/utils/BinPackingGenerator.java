package utils;

import models.Bin;
import models.Item;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BinPackingGenerator {

    private static List<Bin> firstFitBase(List<Item> items, int sizeLimit){
        List<Bin> binList = new ArrayList<>();
        List<Bin> notFullBinList = new ArrayList<>();

        notFullBinList.add(new Bin(sizeLimit));

        for (Item item : items){
            boolean packed = false;

            int i = 0;
            while (i < notFullBinList.size() && !packed) {
                if (notFullBinList.get(i).hasSpace(item)){
                    try {
                        notFullBinList.get(i).add(item);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (notFullBinList.get(i).isFull()){
                        binList.add(notFullBinList.remove(i));
                    }
                    packed = true;
                }
                i++;
            }
            if (!packed){
                Bin bin = new Bin(sizeLimit);
                try {
                    bin.add(item);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                notFullBinList.add(bin);
            }
        }
        for (Bin bin : notFullBinList){
            binList.add(bin);
        }
        return binList;
    }

    public static List<Bin> firstFitDecreasing(List<Item> items, int sizeLimit){
        List<Item> sortedList = items.stream()
                .sorted(Comparator.comparingInt(Item::getSize).reversed())
                .collect(Collectors.toList());
        return firstFitBase(sortedList, sizeLimit);
    }
}
