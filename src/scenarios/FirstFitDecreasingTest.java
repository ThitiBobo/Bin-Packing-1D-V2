package scenarios;

import models.Bin;
import models.BinPackingScenario;
import utils.BinPackingGenerator;
import utils.BinPackingUtils;

import java.util.List;

public class FirstFitDecreasingTest extends ScriptBase{

    public static void main(String[] args) {
        ScriptBase script = new FirstFitDecreasingTest();
        script.start();
    }

    public FirstFitDecreasingTest(){
        super();
    }

    @Override
    protected void init() {
        // INITIALISATION
        System.out.println("[ Script 2 ] : test de la procédure firstFitDecreasing\n");
        initAllBinPackingScenario();
    }

    @Override
    protected void run() {

        try {
            manager.getAllBinPackingScenario().forEach( (key, value) -> {
                List<Bin> binList = BinPackingGenerator.firstFitDecreasing(value.getItemList(), value.getSizeLimit());
                value.setBinList(value.getSizeLimit(), binList);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void display() {

        try {
            manager.getAllBinPackingScenario().forEach( (key, value) -> {
                System.out.println(key);
                BinPackingUtils.displayInfo(value.getBinList());
                BinPackingUtils.display(value.getBinList(), 20);
                System.out.println("");
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
