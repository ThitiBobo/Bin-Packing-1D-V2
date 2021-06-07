package utils;

import models.Bin;
import models.BinPacking;

import java.util.Iterator;

public class BinPackingUtils {

    public static void displayInfo(BinPacking binPacking){
        int nbItem = binPacking.getItemList().size();
        int nbBin = binPacking.getBinList().size();
        int nbTheoreticalBin = binPacking.getTheoreticalMinimumBinNumber();
        int fitness = binPacking.getObjectiveValue();
        System.out.println(String.format("items number : %s  bins number : %s  theoretical bins number : %s  objective value : %s",
                nbItem,
                nbBin,
                nbTheoreticalBin,
                fitness));
    }

    public static void display(BinPacking binPacking){
        display(binPacking, binPacking.getBinList().size());
    }

    public static void display(BinPacking binPacking, int displayLimit){
        int i = 0;
        Iterator iterator = binPacking.getBinList().listIterator();
        while (iterator.hasNext() && i < displayLimit){
            Bin bin = (Bin)iterator.next();
            System.out.print("[ ");
            bin.getList().forEach( item -> {
                System.out.print(item.getSize() + " ");
            });
            System.out.println("] " + bin.getOccupiedSpace() + "/" + bin.getSizeLimit() + " : " + bin.getObjectiveValue());
            i++;
        }
        if (displayLimit < binPacking.getBinList().size()){
            System.out.println(String.format("%s more ...", binPacking.getBinList().size() - displayLimit));
        }
    }
}
