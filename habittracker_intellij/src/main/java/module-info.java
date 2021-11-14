module gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    requires vecmath;
    requires java.sql;
    
    opens org.jap to javafx.fxml;
    exports org.jap.examples;
    opens org.jap.examples to javafx.fxml;
}