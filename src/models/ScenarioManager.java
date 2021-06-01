package models;

import utils.FileUtils;

import java.util.*;

public class ScenarioManager {

    private static ScenarioManager instance;
    private final static String FOLDER_PATH = "resources/data/";

    private TreeMap<String, BinPackingScenario> scenarioList;
    private Map<String, String> binDataList;

    public BinPackingScenario getBinPackingScenario(int index) throws Exception {
        if (this.scenarioList == null) throw new Exception("list of scenario does not exist, you must initialize it");
        return this.scenarioList.get(index);
    }

    public Map<String, BinPackingScenario> getAllBinPackingScenario(){
        if (this.scenarioList == null) initAllBinPackingScenario();
        return this.scenarioList;
    }

    private ScenarioManager(){
        this.scenarioList = new TreeMap<>();
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

    public void initAllBinPackingScenario(){
        this.scenarioList.clear();
        this.binDataList.forEach((key, value) -> {
            AbstractMap.SimpleEntry<Integer, List<Item>> res = calculateItemList(value);
            int sizeLimit = res.getKey();
            List<Item> items = res.getValue();
            this.scenarioList.put(key ,new BinPackingScenario(key, sizeLimit, items));
        });
    }

    private AbstractMap.SimpleEntry<Integer, List<Item>> calculateItemList( String data){
        List<String> dataSplit = new ArrayList<String>(Arrays.asList(data.split(System.getProperty("line.separator"))));

        String firstLine = dataSplit.remove(0);
        String[] firstLineSplit = firstLine.split(" ");
        int sizeLimit = Integer.parseInt(firstLineSplit[0]);
        int nbItem = Integer.parseInt(firstLineSplit[1]);
        List<Item> items = new ArrayList<>();

        for (int i = 0; i < nbItem; i++){
            items.add(new Item(i,Integer.parseInt(dataSplit.get(i))));
        }
        return new AbstractMap.SimpleEntry<>(sizeLimit, items);
    }

    public static ScenarioManager getInstance(){
        if (instance == null){
            ScenarioManager.instance = new ScenarioManager();
        }
        return ScenarioManager.instance;
    }
}