import models.BinPackingScenario;
import models.Item;
import utils.BinPackingGenerator;
import utils.BinPackingUtils;
import utils.Heuristic;

import java.util.ArrayList;
import java.util.List;

public class SimulatedAnnealingTest {

    private static final int SIZE_LIMIT = 4;
    private static final int[] DATA = {1,2,3,2,2,3,1,1,2,1};

    private BinPackingScenario scenario;

    public static void main(String[] args) {
        new SimulatedAnnealingTest();
    }

    public SimulatedAnnealingTest() {

        List<Item> items = new ArrayList<>();
        for(int i = 0; i < DATA.length; i++){
            items.add(new Item(i, DATA[i]));
        }

        this.scenario = new BinPackingScenario("test", SIZE_LIMIT, items);
        this.scenario.setBinList(SIZE_LIMIT, BinPackingGenerator.oneItemPerBinGenerator(items, SIZE_LIMIT));

        System.out.println(this.scenario.getOriginalBinList().hash());
        BinPackingUtils.displayInfo(this.scenario.getOriginalBinList());
        BinPackingUtils.display(this.scenario.getOriginalBinList());

        System.out.println(this.scenario.getBinList().hash());
        BinPackingUtils.displayInfo(this.scenario.getBinList());
        BinPackingUtils.display(this.scenario.getBinList());

        System.out.println(this.scenario.getBestBinList().hash());
        BinPackingUtils.displayInfo(this.scenario.getBestBinList());
        BinPackingUtils.display(this.scenario.getBestBinList());

        Heuristic.simulatedAnnealing(scenario, 500.0, 500, 500, 0.95);
        System.out.println("--------------------------------------------");

        System.out.println(this.scenario.getOriginalBinList().hash());
        BinPackingUtils.displayInfo(this.scenario.getOriginalBinList());
        BinPackingUtils.display(this.scenario.getOriginalBinList());

        BinPackingUtils.displayInfo(this.scenario.getBinList());
        BinPackingUtils.display(this.scenario.getBinList());

        BinPackingUtils.displayInfo(this.scenario.getBestBinList());
        BinPackingUtils.display(this.scenario.getBestBinList());
    }
}
