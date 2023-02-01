package pl.pogoda.view;

public enum ColorTheme {
    LIGHT("/style/light.css"),
    DARK("/style/dark.css");
    public String path;

    ColorTheme(String path){
       this.path = path;
    }

}

