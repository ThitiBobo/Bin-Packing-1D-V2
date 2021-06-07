package scenarios;

import models.Bin;
import models.BinPackingScenario;
import models.Item;
import models.ScenarioManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class ScriptBase {

    protected ScenarioManager manager;
    protected long durationInNano;

    public ScriptBase(){
        this.manager = ScenarioManager.getInstance();
    }

    public void initAllBinPackingScenario(){
        this.manager.initAllBinPackingScenario();
    }

    public BinPackingScenario initBinPackingScenario(int index){
        return this.manager.initBinPackingScenario(index);
    }

    public void start(){
        init();

        long startTime = System.nanoTime();
        run();
        long endTime = System.nanoTime();
        this.durationInNano = (endTime - startTime);

        System.out.println("execution time : " + getDurationTime());
        System.out.println("");
        display();
    }

    public String getDurationTime(){
        long time = this.durationInNano;
        int hours = (int) (time / 3_600_000_000_000L);
        time -= hours * 3_600_000_000_000L;
        int minutes = (int) (time / 60_000_000_000L);
        time -= minutes * 60_000_000_000L;
        int secondes = (int) (time / 1_000_000_000L);
        time -= secondes * 1_000_000_000L;
        double millisecondes = (double)time / 1_000_000.0;

        StringBuilder builder = new StringBuilder();

        if (hours < 10) builder.append("0");
        builder.append(hours).append(":");
        if (minutes < 10) builder.append("0");
        builder.append(minutes).append(":");
        if (secondes < 10) builder.append("0");
        builder.append(secondes);
        builder.append(" ");
        builder.append("(").append(millisecondes).append(")");

        return builder.toString();
    }

    public BinPackingScenario generateScenario(int sizeLimit, int nbItem){
        Random random = new Random();
        List<Item> itemList = new ArrayList<>();
        for (int i = 0; i < nbItem; i++){
            itemList.add(new Item(i, random.nextInt(sizeLimit - 2) + 2));
        }
        return new BinPackingScenario("", sizeLimit, itemList);
    }

    protected abstract void init();
    protected abstract void run();
    protected abstract void display();
}
