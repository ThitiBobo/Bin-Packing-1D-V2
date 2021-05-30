package scenarios;

import models.Bin;
import utils.BinPackingGenerator;
import utils.BinPackingUtils;

import java.util.List;

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
                BinPackingUtils.displayInfo(value.getBinList());
                BinPackingUtils.display(value.getBinList(), 10);
                System.out.println("");
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
