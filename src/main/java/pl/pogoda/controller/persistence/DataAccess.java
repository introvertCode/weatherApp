package pl.pogoda.controller.persistence;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DataAccess {

    private String USER_CITIES_LOCATION = System.getProperty("user.home") + "\\AppData\\Roaming\\WeatherApp";
    private String USER_CITIES_FILE = System.getProperty("user.home") + "\\AppData\\Roaming\\WeatherApp\\weatherAppData.txt";

    public void createFile(){
        try {
            new File(USER_CITIES_LOCATION).mkdirs();
            File myObj = new File(USER_CITIES_FILE);
            myObj.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToFile(List<String> dataToWrite){
        if(ifFileExists()) {
            try {
                Path path = Paths.get(USER_CITIES_FILE);
                BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8);
                for (String arg : dataToWrite) {
                    writer.append(arg + "\n");
                    writer.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<String> readFromFile(){
        List<String> retrievedData = new ArrayList<>();
        if(ifFileExists()) {
            try {
                File myObj = new File(USER_CITIES_FILE);
                FileInputStream fis = new FileInputStream(myObj);
                InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
                BufferedReader reader = new BufferedReader(isr);
                String str;
                while ((str = reader.readLine()) != null) {
                    retrievedData.add(str);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return retrievedData;
    }

    private boolean ifFileExists(){
        File f = new File(USER_CITIES_FILE);
        if(f.exists() && !f.isDirectory()) {
            return true;
        } else {
            return false;
        }
    }
}
