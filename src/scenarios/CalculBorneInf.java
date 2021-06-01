package scenarios;

import models.ScenarioManager;
import utils.FileUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalculBorneInf extends ScriptBase{

    private Map<String, Integer> results;

    public static void main(String[] args) {
        ScriptBase script = new CalculBorneInf();
        script.start();
    }

    public CalculBorneInf(){
        super();
        this.results = new HashMap<>();
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
            manager.getAllBinPackingScenario().forEach( (key, binPackingScenario) -> {
                this.results.put(key ,binPackingScenario.getTheoreticalMinimumBinNumber());
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void display() {
        System.out.println("execution time : " + getDurationTime());
        System.out.println();
        this.results.forEach( (key, value) -> {
            System.out.println(key + " : " + value);
        });
        System.out.println();
    }
}
