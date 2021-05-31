module Biodiversity {

    // configuration files except pom and .gitignore to resources

    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires com.fasterxml.jackson.databind;
    requires org.apache.logging.log4j;
    opens biodiversity;
    exports biodiversity.controller to com.fasterxml.jackson.databind;
    exports biodiversity.view to javafx.graphics;

}