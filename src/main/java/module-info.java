module pogoda{
    requires javafx.fxml;

    requires javafx.controls;
    requires openweathermap.api;
    requires org.apache.commons.lang3;

    opens pl.pogoda;
    opens pl.pogoda.controller;

    }