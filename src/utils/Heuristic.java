package utils;

import jdk.jshell.spi.ExecutionControl;
import models.BinPackingScenario;
import models.operations.MoveOperation;
import models.operations.Operation;
import models.operations.SwitchOperation;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Heuristic {

    public static void simulatedAnnealing(BinPackingScenario scenario, double tinit, int nmax, int kmax, double mu){
        double t = tinit;
        for (int n = 0 ; n < nmax; n++){
            for (int k = 0; k < kmax; k++){
                Operation neighborhood = scenario.getRandomOperation();

                int delta = neighborhood.getObjectiveValue() - scenario.getObjectiveValue();

                if (delta >= 0){
                    int bestObjectiveValue = scenario.getBestObjectiveValue();
                    applyOperation(scenario, neighborhood);
                    if (scenario.getObjectiveValue() > bestObjectiveValue){
                        scenario.updateBestScenario();
                    }

                } else {
                    double p = Math.random();
                    if (p <= Math.exp(-delta / t)) {
                        applyOperation(scenario, neighborhood);
                    }
                }
            }
            t *= mu;
        }
    }

    public static void tabuSearch(BinPackingScenario scenario, int nmax, int size){
        // TODO comment retenir les move avec bin, si les bin disparaissent ?
        Queue<String> tabuList = new LinkedList<>();
        tabuList.add(scenario.getBinList().hash());
        int n = 0;
        while (n < nmax){
            List<Operation> operations = scenario.getBinList().getAllNeighborhoodOperation();
            Operation bestOp = operations.get(0);
            for (Operation op : operations){
                if (!tabuList.contains(op.getHash()) && op.getObjectiveValue() > bestOp.getObjectiveValue()){
                    bestOp = op;
                }
            }



            n++;
        }

    }

    private static void applyOperation(BinPackingScenario scenario, Operation operation){
        try {
            if (operation instanceof SwitchOperation) {
                SwitchOperation op = (SwitchOperation) operation;
                scenario.getBinList().switchItem(op);
                scenario.updateHistory(op);
            }
            if (operation instanceof MoveOperation) {
                MoveOperation op = (MoveOperation) operation;
                scenario.getBinList().moveItem(op);
                scenario.updateHistory(op);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
