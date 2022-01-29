package org.jap.view;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jap.model.datahandler.DataListProvider;

/*
    Created by Peter
*/

/**
 * Startup Class for normal App Usage
 */
public class App extends Application {
    private static final Logger log = LogManager.getLogger(App.class);
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void init() {
        // Initialisierung
        log.debug("Initializing complete");
    }
    
    @Override
    public void start(Stage stage) {
        // Programmstart
        log.info("Starting Startup View");
        
//        stage.initStyle(StageStyle.UTILITY);
        
        SceneManager sceneManager = new SceneManager(stage);

//        stage.setTitle("This is our wonderful Mood-Trekker-Application. Look at this fancy title!");
        stage.setTitle("Moodtracker");
        stage.setResizable(false);
        stage.show();
    }
    
    @Override
    public void stop() {
        // ProgrammEnde
        log.info("Program Closes");
    }
}
