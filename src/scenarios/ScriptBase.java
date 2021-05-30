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

        display();
    }

    public String getDurationTime(){
        // TODO à amélioré
        long milli = this.durationInNano % 1_000_000;
        int sec = (int) (this.durationInNano / 1_000_000_000);

        StringBuilder builder = new StringBuilder();
        builder.append("00:00:").append(sec);
        builder.append(" ");
        builder.append("(").append(milli).append(")");

        return builder.toString();
    }

    protected abstract void init();
    protected abstract void run();
    protected abstract void display();
}
