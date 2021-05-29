package utils;

import models.BinPacking;

public class BinUtils {

    public static void display(BinPacking binPacking){
        binPacking.getBinList().forEach( bin -> {
            System.out.print("[ ");
            bin.getList().forEach( item -> {
                System.out.print(item.getSize() + " ");
            });
            System.out.println();
        });
    }
}
