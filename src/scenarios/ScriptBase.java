package scenarios;

import models.ScenarioManager;

public abstract class ScriptBase {

    protected ScenarioManager manager;
    protected long durationInNano;

    public ScriptBase(){
        this.manager = ScenarioManager.getInstance();
    }

    public void initAllBinPackingScenario(){
        this.manager.initAllBinPackingScenario();
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

    protected abstract void init();
    protected abstract void run();
    protected abstract void display();
}
