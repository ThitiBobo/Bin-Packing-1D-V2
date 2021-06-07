package scenarios.test;

import models.Bin;
import models.BinPackingScenario;
import scenarios.ScriptBase;
import utils.BinPackingGenerator;
import utils.BinPackingUtils;
import utils.Heuristic;

import java.util.ArrayList;
import java.util.List;

public class SimuAnnealingNKMaxTest extends ScriptBase {

    public static final int T_INIT = 500;
    public static final int N_MAX = 500;
    public static final int K_MAX = 500;
    public static final double MU = 0.98;

    public static final int K_MAX_START = 10;
    public static final int N_MAX_START = 10;
    public static final int STEP = 10;
    public static final int NB_TEST = 50;

    private int k_max;
    private int n_max;

    private BinPackingScenario scenario;

    private List<Integer> fitnessList;
    private List<Integer> binNumberList;

    public static void main(String[] args) {
        ScriptBase script = new SimuAnnealingNKMaxTest();
        script.start();
    }

    public SimuAnnealingNKMaxTest(){
        super();
        this.fitnessList = new ArrayList<>();
        this.binNumberList = new ArrayList<>();
        this.k_max = K_MAX_START;
        this.n_max = N_MAX_START;
    }

    @Override
    public void start() {
        this.scenario = generateScenario(10, 200);
        List<Bin> binList = BinPackingGenerator.oneItemPerBinGenerator(scenario.getItemList(), scenario.getSizeLimit());

        for (int i = 0; i < NB_TEST + 1; i ++){
            this.scenario.setBinList(scenario.getSizeLimit(), new ArrayList<>(binList));
            this.k_max = K_MAX_START + (STEP * i);
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
            int k_max = K_MAX_START + (STEP * i);
            int n_max = N_MAX_START + (STEP * i);
            System.out.println(k_max + ";" + n_max + ";" + this.binNumberList.get(i) + ";" + this.fitnessList.get(i));
        }
    }

    @Override
    protected void init() {

    }

    @Override
    protected void run() {
        System.out.println("bin: " + scenario.getBinList().getBinList().size());
        Heuristic.simulatedAnnealing(scenario, T_INIT, n_max, k_max, MU);
    }

    @Override
    protected void display() {
        BinPackingUtils.displayInfo(scenario.getBestBinList());
        System.out.println("K_MAX : " + this.k_max + "  N_MAX : " + this.n_max);
        // BinPackingUtils.display(scenario.getBestBinList(), 20);
        System.out.println("");
    }
}
