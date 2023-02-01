package pl.pogoda.controller.persistence;

import java.util.List;

public class SavedState {
    String homeCity="";
    String destinationCity="";
    String colorTheme ="";

    public SavedState() {
        loadDataFromFile();
    }

    private void loadDataFromFile(){
        DataAccess dataAccess = new DataAccess();
        List<String> savedData = dataAccess.readFromFile();
        if (savedData.size()>2) {
            colorTheme = savedData.get(0);
            homeCity = savedData.get(1);
            destinationCity = savedData.get(2);
        }
    }

    public String getSavedHomeCity(){
        return homeCity;
    }

    public String getSavedDestinationCity(){
        return destinationCity;
    }

    public String getSavedColorTheme(){
        return colorTheme;
    }

}
