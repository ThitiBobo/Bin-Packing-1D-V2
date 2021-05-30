package models;

import utils.FileUtils;

import java.util.*;

public class ScenarioManager {

    private static ScenarioManager instance;
    private final static String FOLDER_PATH = "resources/data/";

    private List<BinPackingScenario> scenarioList;
    private Map<String, String> binDataList;

    private ScenarioManager(){
        this.scenarioList = new ArrayList<>();
        this.binDataList = new HashMap<>();

        List<String> folderList = FileUtils.listFilesForFolder(FOLDER_PATH);
        List<String> dataList = FileUtils.readFiles(folderList);

        Iterator iterator = folderList.listIterator();

        dataList.forEach( data -> {
            if (!iterator.hasNext()) try {
                throw new Exception("An error occurred.");
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.binDataList.put(iterator.next().toString(),data);
        });
    }

    public static ScenarioManager getInstance(){
        if (instance == null){
            ScenarioManager.instance = new ScenarioManager();
        }
        return ScenarioManager.instance;
    }
}
