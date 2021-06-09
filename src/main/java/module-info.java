module Biodiversity {

    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires com.fasterxml.jackson.databind;
    requires org.apache.logging.log4j;
    opens biodiversity;
    opens biodiversity.view;
    exports biodiversity.controller to com.fasterxml.jackson.databind;
    exports biodiversity.view to javafx.graphics;


}