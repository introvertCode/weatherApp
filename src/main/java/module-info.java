module pogoda{
    requires javafx.fxml;

    requires javafx.controls;
    requires openweathermap.api;
    requires java.desktop;

    opens pl.pogoda;
    opens pl.pogoda.controller;
    opens pl.pogoda.model;

}