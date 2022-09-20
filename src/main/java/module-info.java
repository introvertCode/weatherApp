module pogoda{
    requires javafx.fxml;

    requires javafx.controls;
    requires openweathermap.api;

    opens pl.pogoda;
    opens pl.pogoda.controller;

    }