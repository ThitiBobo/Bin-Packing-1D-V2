package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class FileUtils {

    public static List<String> listFilesForFolder(String folderPath){
        File folder = new File(folderPath);
        return listFilesForFolder(folder);
    }

    public static List<String> listFilesForFolder(File folder) {
        List<String> list = new ArrayList<>();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isFile()){
                list.add(fileEntry.toString());
            }
        }
        return list.stream().sorted().collect(Collectors.toList());
    }

    public static List<String> readFiles( List<String> list){
        List<File> fileList = list.stream().map(f -> { return new File(f); }).collect(Collectors.toList());
        List<String> result = new ArrayList<>();
        fileList.forEach(f -> {
            result.add(readFile(f));
        });
        return result;
    }

    public static String readFile(String filePath){
        File file = new File(filePath);
        return readFile(file);
    }

    public static String readFile(File file){

        StringBuilder data = new StringBuilder();
        try {
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                data.append(reader.nextLine()).append(System.getProperty("line.separator"));
            }
            reader.close();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return data.toString();
    }
}
