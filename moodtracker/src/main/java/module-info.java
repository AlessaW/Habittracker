module gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    requires org.json;
    
//    opens org.examples to javafx.fxml;    // uncomment if examples are needed
//    exports org.examples;
    opens org.jap.controller to javafx.fxml;    // javafx needs the controller classes
    opens org.jap.model.mood to org.json;
    exports org.jap.view;
}