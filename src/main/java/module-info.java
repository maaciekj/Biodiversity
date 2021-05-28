module Biodiversity {

    // pliki konfiguracyjne oprócz pom i gitignore w resources

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