package org.jap.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
    Created by Peter
*/

/**
 * Startup Class for normal App Usage
 */
public class StartupView extends Application {
    private static final Logger log = LogManager.getLogger(StartupView.class);
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void init() {
        // Initialisierung
        log.debug("Initializing complete");
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        // Programmstart
        log.info("Starting Startup View");
        
        final String fxmlFile = "/fxml/startwindow.fxml";
        log.debug("Loading FXML for main view from: {}", fxmlFile);
        final FXMLLoader loader = new FXMLLoader();
        final Parent startupMenuNode = loader.load(getClass().getResourceAsStream(fxmlFile));
        
        log.debug("Showing JFX scene");
        final Scene startupMenuScene = new Scene(startupMenuNode, 640, 400);
        
        stage.setTitle("This is our wonderful Mood-Trekker-Application. Look at this fancy title!");
        stage.setScene(startupMenuScene);
        stage.show();
    }
    
    @Override
    public void stop() {
        // ProgrammEnde
        log.info("Program Closes");
    }
}
