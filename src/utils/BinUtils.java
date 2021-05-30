package utils;

import models.BinPacking;

public class BinUtils {

    public static void displayInfo(BinPacking binPacking){
        int nbItem = binPacking.getItemList().size();
        int nbBin = binPacking.getBinList().size();
        int nbTheoreticalBin = binPacking.getTheoreticalMinimumBinNumber();
        System.out.println(String.format("items number : %s  bins number : %s  theoretical bins number : %s",
                nbItem,
                nbBin,
                nbTheoreticalBin));
    }

    public static void display(BinPacking binPacking){
        binPacking.getBinList().forEach( bin -> {
            System.out.print("[ ");
            bin.getList().forEach( item -> {
                System.out.print(item.getSize() + " ");
            });
            System.out.println("] " + bin.getOccupiedSpace() + "/" + bin.getSizeLimit());
        });
    }
}
