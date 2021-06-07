package scenarios;

import models.Bin;
import utils.BinPackingGenerator;
import utils.BinPackingUtils;
import utils.Heuristic;

import java.util.List;

public class TabuListTest extends ScriptBase{

    public static final int N_MAX = 500;
    public static final int SIZE = 20;

    public static void main(String[] args) {
        ScriptBase script = new TabuListTest();
        script.start();
    }

    public TabuListTest(){
        super();
    }

    @Override
    protected void init() {
        // INITIALISATION
        System.out.println("[ Script 5 ] : test de la méthode recherche tabou\n");
        initAllBinPackingScenario();

        try {
            manager.getAllBinPackingScenario().forEach( (key, value) -> {
                List<Bin> binList = BinPackingGenerator.oneItemPerBinGenerator(value.getItemList(), value.getSizeLimit());
                value.setBinList(value.getSizeLimit(), binList);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void run() {
        try {
            manager.getAllBinPackingScenario().forEach( (key, value) -> {
                Heuristic.tabuSearch(value, N_MAX, SIZE);

                System.out.println(key);
                BinPackingUtils.displayInfo(value.getBestBinList());
                BinPackingUtils.display(value.getBestBinList(), 10);
                System.out.println("");

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void display() {

        manager.getAllBinPackingScenario().forEach( (key, value) -> {
            System.out.println(key);
            BinPackingUtils.displayInfo(value.getBestBinList());
            BinPackingUtils.display(value.getBestBinList());
            System.out.println("");
        });

    }
}
