module gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;

    opens org.jap.view to javafx.fxml;
    exports org.jap.view;
}