package pl.pogoda.view;

public enum ColorTheme {
    LIGHT,
    DARK;
    public static String getCssPath(ColorTheme colorTheme){
        switch(colorTheme) {
            case LIGHT:
                return "/style/light.css";
            case DARK:
                return "/style/dark.css";
            default:
                return null;
        }
    }
}

