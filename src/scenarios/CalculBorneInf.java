package scenarios;

import models.BinPackingScenario;
import models.Pair;
import models.ScenarioManager;
import utils.FileUtils;

import java.util.*;

public class CalculBorneInf extends ScriptBase{

    private List<Pair<String, Integer>> results;

    public static void main(String[] args) {
        ScriptBase script = new CalculBorneInf();
        script.start();
    }

    public CalculBorneInf(){
        super();
        this.results = new ArrayList<>();
    }

    @Override
    protected void init() {
        // INITIALISATION
        System.out.println("[ Script 1 ] : calcule bornes inférieurs (théorique)\n");
        initAllBinPackingScenario();
    }

    @Override
    protected void run() {
        try {
            manager.getAllBinPackingScenario().forEach( (key, value) -> {
                this.results.add(new Pair<>(key, value.getTheoreticalMinimumBinNumber()));
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void display() {

        this.results.forEach( pair -> {
            System.out.println(pair.getL() + " : " + pair.getR());
        });
        System.out.println();
    }
}
