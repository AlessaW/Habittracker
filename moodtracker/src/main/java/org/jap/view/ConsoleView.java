package org.jap.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jap.util.MiaUtils;

import java.net.URI;
import java.net.URL;
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
        log.info("Starting Console View");
    
        final String fxmlFile = "/fxml/GUI_Console.fxml"; // module-info: opens org.jap.controller to javafx.fxml; // package of the controller class
        log.debug("Loading FXML for main view from: {}", fxmlFile);
        final FXMLLoader loader = new FXMLLoader();
        final Parent rootNode = loader.load(getClass().getResourceAsStream(fxmlFile));
    
        log.debug("Showing JFX scene");
        final Scene scene = new Scene(rootNode, 1280, 720);
        //scene.getStylesheets().add("/styles/styles.css");
    
        stage.setTitle("Hello JavaFX and Maven");
        stage.setScene(scene);
        stage.show();
    }
    
    @Override
    public void stop() {
        // ProgrammEnde
        log.info("Program Closes");
    }
}
