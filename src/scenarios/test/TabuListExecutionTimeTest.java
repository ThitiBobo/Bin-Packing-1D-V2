package scenarios.test;

import models.Bin;
import models.BinPackingScenario;
import models.Item;
import scenarios.ScriptBase;
import scenarios.TabuListTest;
import utils.BinPackingGenerator;
import utils.BinPackingUtils;
import utils.Heuristic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TabuListExecutionTimeTest extends ScriptBase {

    public static final int SIZE_LIMIT = 9;
    public static final int MIN_ITEM = 20;
    public static final int MAX_ITEM = 500;
    public static final int NB_SCENARIO = 48;

    public static final int N_MAX = 500;
    public static final int SIZE = 20;

    private List<BinPackingScenario> scenarioList;
    private List<String> durations;
    private BinPackingScenario scenario;
    private int nbItem;

    public static void main(String[] args) {
        ScriptBase script = new TabuListExecutionTimeTest();
        script.start();
    }

    public TabuListExecutionTimeTest(){
        super();
        this.scenarioList = new ArrayList<>();
        this.durations = new ArrayList<>();
        this.nbItem = 0;
    }

    @Override
    public void start() {
        System.out.println("[ TEST ] : test du temps d'exécution en fonction de la taille de la liste d'item\n");
        for (int i = 0; i < NB_SCENARIO + 1; i ++){
            this.nbItem = i * ( (MAX_ITEM - MIN_ITEM) / NB_SCENARIO) + MIN_ITEM;

            init();
            long startTime = System.nanoTime();
            run();
            long endTime = System.nanoTime();
            this.durationInNano = (endTime - startTime);

            durations.add(getDurationTime());
            System.out.println("execution time : " + getDurationTime());
            System.out.println("");
            display();
        }
    }

    @Override
    protected void init() {
        // INITIALISATION
        this.scenario = generateScenario(SIZE_LIMIT, this.nbItem);
        List<Bin> binList = BinPackingGenerator.oneItemPerBinGenerator(scenario.getItemList(), scenario.getSizeLimit());
        this.scenario.setBinList(scenario.getSizeLimit(), binList);
        this.scenarioList.add(scenario);
    }

    @Override
    protected void run() {
        Heuristic.tabuSearch(scenario, N_MAX, SIZE);
    }

    @Override
    protected void display() {
        BinPackingUtils.displayInfo(scenario.getBestBinList());
        // BinPackingUtils.display(scenario.getBestBinList(), 20);
        System.out.println("");
    }
}
