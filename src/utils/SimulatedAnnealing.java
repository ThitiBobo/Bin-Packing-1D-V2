package utils;

import models.BinPackingScenario;
import models.operations.MoveOperation;
import models.operations.Operation;
import models.operations.SwitchOperation;

public class SimulatedAnnealing {

    public static void simulatedAnnealing(BinPackingScenario scenario, double tinit, int nmax, int kmax, double mu){
        int i = 0;
        double t = tinit;
        for (int n = 0 ; n < nmax; n++){
            for (int k = 0; k < kmax; k++){
                // TODO get a neighborhood
                Operation neighborhood = null;

                int delta = neighborhood.getObjectiveValue() - scenario.getObjectiveValue();

                if (delta >= 0){
                    int bestObjectiveValue = scenario.getBestObjectiveValue();
                    applyOperation(scenario, neighborhood);
                    if (scenario.getObjectiveValue() > bestObjectiveValue){
                        //scenario.updateBestScenario();
                    }

                } else {
                    double p = Math.random();
                    if (p <= Math.exp(-delta / t)) {
                        applyOperation(scenario, neighborhood);
                    }
                }
                i++;
            }
            t *= mu;
        }
    }

    private static void applyOperation(BinPackingScenario scenario, Operation operation){
        try {
            if (operation instanceof SwitchOperation) {
                SwitchOperation op = (SwitchOperation) operation;
                scenario.getBinList().switchItem(op.getItem1(), op.getItem2());
            }
            if (operation instanceof MoveOperation) {
                MoveOperation op = (MoveOperation) operation;
                scenario.getBinList().moveItem(op.getItem(), op.getBin());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
