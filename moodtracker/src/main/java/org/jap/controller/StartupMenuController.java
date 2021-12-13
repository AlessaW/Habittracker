package org.jap.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
    Created by Peter
*/

/**
 * The Controller Class for the Startup View
 */
public class StartupMenuController {
    private static final Logger log = LogManager.getLogger(StartupMenuController.class);
    
    // Variables
    @FXML private VBox   root;          // Name im FXML File als fx:id angegeben
    @FXML private Button btnAddMood;
    @FXML private Button btnEditMood;
    @FXML private Button btnViewStats;
    @FXML private Button btnExit;
    
    private Stage stage;
    
    // Constructor
    /**
     * Constructor can be used to do some view initialization
     */
    public StartupMenuController() {
        // Maybe Todo: view initialization
//        root.getScene().getWindow();
    }
    
    // Methods
    @FXML public void btnAddMoodAction() {   // Name im FXML File als Action Event angegeben
        // Todo: switch scene to createMood
        log.debug("AddMood Button Clicked");
    }
    @FXML public void btnEditMoodAction() {   // Name im FXML File als Action Event angegeben
        // Todo: important stuff
        log.debug("EditMood Button Clicked");
    }
    @FXML public void btnViewStatsAction() {   // Name im FXML File als Action Event angegeben
        // Todo: important stuff
        log.debug("ViewStats Button Clicked");
    }
    @FXML public void btnExitAction() {   // Name im FXML File als Action Event angegeben
        log.debug("Exit Button Clicked");
        exitApplication();
    }
    
    private void exitApplication() {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
    }
}
