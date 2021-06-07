package scenarios.test;

import models.Bin;
import models.BinPackingScenario;
import scenarios.ScriptBase;
import utils.BinPackingGenerator;
import utils.BinPackingUtils;
import utils.Heuristic;

import java.util.ArrayList;
import java.util.List;

public class SimuAnnealingNMaxTest extends ScriptBase {

    public static final int T_INIT = 500;
    public static final int N_MAX = 500;
    public static final int K_MAX = 500;
    public static final double MU = 0.98;

    public static final int N_MAX_START = 10;
    public static final int STEP = 10;
    public static final int NB_TEST = 50;

    private int n_max;

    private BinPackingScenario scenario;

    private List<Integer> fitnessList;
    private List<Integer> binNumberList;

    public static void main(String[] args) {
        ScriptBase script = new SimuAnnealingNMaxTest();
        script.start();
    }

    public SimuAnnealingNMaxTest(){
        super();
        this.fitnessList = new ArrayList<>();
        this.binNumberList = new ArrayList<>();
        this.n_max = N_MAX_START;
    }

    @Override
    public void start() {
        this.scenario = generateScenario(10, 200);
        List<Bin> binList = BinPackingGenerator.oneItemPerBinGenerator(scenario.getItemList(), scenario.getSizeLimit());

        for (int i = 0; i < NB_TEST + 1; i ++){
            this.scenario.setBinList(scenario.getSizeLimit(), new ArrayList<>(binList));
            this.n_max = N_MAX_START + (STEP * i);

            long startTime = System.nanoTime();
            run();
            long endTime = System.nanoTime();
            this.durationInNano = (endTime - startTime);

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

    }

    @Override
    protected void run() {
        System.out.println("bin: " + scenario.getBinList().getBinList().size());
        Heuristic.simulatedAnnealing(scenario, T_INIT, n_max, K_MAX, MU);
    }

    @Override
    protected void display() {
        BinPackingUtils.displayInfo(scenario.getBestBinList());
        System.out.println("N_MAX : " + this.n_max);
        // BinPackingUtils.display(scenario.getBestBinList(), 20);
        System.out.println("");
    }
}
