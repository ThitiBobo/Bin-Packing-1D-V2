package scenarios;

import models.Bin;
import utils.BinPackingGenerator;
import utils.BinPackingUtils;

import java.util.List;

public class RandomGeneratorTest extends ScriptBase{

    public static void main(String[] args) {
        ScriptBase script = new RandomGeneratorTest();
        script.start();
    }

    public RandomGeneratorTest(){
        super();
    }

    @Override
    protected void init() {
        // INITIALISATION
        System.out.println("[ Script 3 ] : test du générateur aléatoire randomFirstFit\n");
        initAllBinPackingScenario();
    }

    @Override
    protected void run() {
        try {
            manager.getAllBinPackingScenario().forEach( (key, value) -> {
                List<Bin> binList = BinPackingGenerator.randomFirstFit(value.getItemList(), value.getSizeLimit());
                value.setBinList(value.getSizeLimit(), binList);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void display() {
        try{
            manager.getAllBinPackingScenario().forEach( (key, value) -> {
                System.out.println(key);
                BinPackingUtils.displayInfo(value.getBinList());
                BinPackingUtils.display(value.getBinList());
                System.out.println("");
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
