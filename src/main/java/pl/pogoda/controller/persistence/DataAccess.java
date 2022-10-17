package pl.pogoda.controller.persistence;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
                FileWriter myWriter = new FileWriter(USER_CITIES_FILE);
                for (String arg : dataToWrite) {
                    myWriter.write(arg + "\n");
                }
                myWriter.close();

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
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    retrievedData.add(data);
                }
                myReader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
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
