package utils;

import models.Bin;
import models.Item;
import models.Pair;

import java.util.ArrayList;
import java.util.List;

public class CombinationGenerator {

    public static List<Pair<Item, Item>> combination(List<Item> items){
        List<Pair<Item, Item>> combinations = new ArrayList<>();
        for (int i = 0; i < items.size(); i++){
            for (int j = (i+1); j < items.size(); j++){
                combinations.add(new Pair<>(items.get(i), items.get(j)));
            }
        }
        return combinations;
    }

    public static List<Pair<Item, Bin>> combination(List<Item> items, List<Bin> bins){
        List<Pair<Item, Bin>> combinations = new ArrayList<>();
        for (Item item : items){
            for (Bin bin : bins){
                combinations.add(new Pair<>(item,bin));
            }
        }
        return combinations;
    }
}
