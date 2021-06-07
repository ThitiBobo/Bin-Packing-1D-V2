package scenarios.test;

import models.Bin;
import models.BinPackingScenario;
import models.Item;
import scenarios.ScriptBase;
import utils.BinPackingGenerator;
import utils.BinPackingUtils;
import utils.Heuristic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TabuListPerfTest extends ScriptBase{

    public static final int SIZE_LIMIT = 10;
    public static final int NB_ITEM = 200;

    public static final int N_MAX = 10;
    public static final int N = 10;
    public static final int NB_SCENARIO = 70;
    public static final int SIZE = 20;

    private List<String> durations;
    private BinPackingScenario scenario;
    private int n_max;

    private List<Integer> fitnessList;
    private List<Integer> binNumberList;

    public static void main(String[] args) {
        ScriptBase script = new TabuListPerfTest();
        script.start();
    }

    public TabuListPerfTest(){
        super();
        this.durations = new ArrayList<>();
        this.fitnessList = new ArrayList<>();
        this.binNumberList = new ArrayList<>();
        this.n_max = 0;
    }

    @Override
    public void start() {
        System.out.println("[ TEST ] : test du temps d'exécution en fonction de la taille de la liste d'item\n");


        this.scenario = generateScenario(SIZE_LIMIT, NB_ITEM);
        List<Bin> binList = BinPackingGenerator.oneItemPerBinGenerator(scenario.getItemList(), scenario.getSizeLimit());


        for (int i = 0; i < NB_SCENARIO + 1; i ++){
            this.scenario.setBinList(scenario.getSizeLimit(), binList);
            this.n_max = N_MAX + (N * i);
            long startTime = System.nanoTime();
            run();
            long endTime = System.nanoTime();
            this.durationInNano = (endTime - startTime);

            durations.add(getDurationTime());
            fitnessList.add(this.scenario.getBestObjectiveValue());
            binNumberList.add(this.scenario.getBestBinList().getBinList().size());

            System.out.println("execution time : " + getDurationTime());
            // System.out.println("");
            display();
        }

        for (int i = 0; i < this.binNumberList.size(); i++){
            System.out.println(this.binNumberList.get(i) + ";" + this.fitnessList.get(i));
        }
    }

    @Override
    protected void init() {
        // INITIALISATION

    }

    @Override
    protected void run() {
        Heuristic.tabuSearch(scenario, n_max, SIZE);
    }

    @Override
    protected void display() {
        BinPackingUtils.displayInfo(scenario.getBestBinList());
        System.out.println("N_MAX : " + this.n_max);
        // BinPackingUtils.display(scenario.getBestBinList(), 20);
        System.out.println("");
    }

}
