package org.jap.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

/*
    Created by Peter
*/

/**
 *  Testklasse zum Testen blabla und so
 */
public class ConsoleView extends Application {
    private static final Logger log = LogManager.getLogger(ConsoleView.class);
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void init() {
        // Initialisierung
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        // Programmstart
        StackPane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("src/main/resources/fxml/GUI_Console.fxml")));
        Scene scene = new Scene(root, 300, 300);
        
//        Button btn = new Button("Hello World");
//        root.getChildren().add(btn);
        
        stage.setTitle("Console JavaFX test");
        stage.setScene(scene);
        stage.show();
    }
    
    @Override
    public void stop() {
        // ProgrammEnde
    }
}
