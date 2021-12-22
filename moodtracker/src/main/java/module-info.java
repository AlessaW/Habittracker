module gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    
//    opens org.examples to javafx.fxml;    // uncomment if examples are needed
//    exports org.examples;
    opens org.jap.controller to javafx.fxml;    // javafx needs the controller classes
    exports org.jap.view;
}