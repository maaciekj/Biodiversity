module Biodiversity {

    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires com.fasterxml.jackson.databind;
    opens biodiversity;
    exports biodiversity.controller to com.fasterxml.jackson.databind;

}