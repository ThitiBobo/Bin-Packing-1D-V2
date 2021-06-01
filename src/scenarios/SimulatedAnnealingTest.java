package scenarios;

import models.Bin;
import utils.BinPackingGenerator;
import utils.BinPackingUtils;
import utils.SimulatedAnnealing;

import java.util.List;

public class SimulatedAnnealingTest extends ScriptBase{

    public static final double T_INIT = 500.0;
    public static final int N_MAX = 200;
    public static final int K_MAX = 200;
    public static final double MU = 0.95;

    public static void main(String[] args) {
        ScriptBase script = new SimulatedAnnealingTest();
        script.start();
    }

    public SimulatedAnnealingTest(){
        super();
    }

    @Override
    protected void init() {
        // INITIALISATION
        System.out.println("[ Script 4 ] : test de la méthode recuit simulé\n");
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

        SimulatedAnnealing.simulatedAnnealing(
                manager.getAllBinPackingScenario().get("resources/data/binpack1d_00.txt"),
                T_INIT,
                N_MAX,
                K_MAX,
                MU
        );
//        try {
//            manager.getAllBinPackingScenario().forEach( (key, value) -> {
//                SimulatedAnnealing.simulatedAnnealing(value, T_INIT, N_MAX, K_MAX, MU);
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
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
