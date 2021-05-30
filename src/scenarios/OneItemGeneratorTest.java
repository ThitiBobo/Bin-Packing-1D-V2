package scenarios;

import models.Bin;
import models.BinPacking;
import utils.BinPackingGenerator;
import utils.BinUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class OneItemGeneratorTest extends ScriptBase{


    public static void main(String[] args) {
        ScriptBase script = new OneItemGeneratorTest();
        script.start();
    }

    public OneItemGeneratorTest(){
        super();
    }

    @Override
    protected void init() {
        // INITIALISATION
        System.out.println("[ Script 4 ] : test du générateur aléatoire oneItemPerBinGenerator\n");
        initAllBinPackingScenario();
    }

    @Override
    protected void run() {
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
    protected void display() {
        try{
            manager.getAllBinPackingScenario().forEach( (key, value) -> {
                System.out.println(key);
                BinUtils.displayInfo(value.getBinPacking());
                BinUtils.display(value.getBinPacking(), 10);
                System.out.println("");
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
