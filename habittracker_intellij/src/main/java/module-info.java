module gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;

    opens org.jap to javafx.fxml;
    exports org.jap;
    exports org.jap.examples;
    opens org.jap.examples to javafx.fxml;
}