package org.jap.controller;

import javafx.fxml.FXML;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jap.model.mood.MoodData;
import org.jap.model.mood.MoodManager;
import org.jap.view.SceneManager;

/*
    Created by Peter
*/

/**
 * The Controller Class for the Startup View
 */
public class StartupMenuController extends GenericController {
    private static final Logger log = LogManager.getLogger(StartupMenuController.class);
    
    // Methods
    @Override
    public void activate() {
        super.activate();
    }
    @Override
    public void deactivate() {
        super.deactivate();
    }
    
    @FXML public void btnAddMoodAction() {   // Name im FXML File als Action Event angegeben
        log.debug("AddMood Button Clicked");
        getSceneManager().switchScene(SceneManager.States.CREATE_MOOD);
    }
    @FXML public void btnEditMoodAction() {   // Name im FXML File als Action Event angegeben
        log.debug("EditMood Button Clicked");
        // Todo: important stuff
        
        getSceneManager().switchScene(SceneManager.States.MOOD_LIST_VIEW);
    }
    @FXML public void btnViewStatsAction() {   // Name im FXML File als Action Event angegeben
        log.debug("ViewStats Button Clicked");
        // Todo: important stuff
        
        log.debug("Saved "+MoodManager.getInstance().getMoods().size()+" Moods: ");
        for (MoodData m : MoodManager.getInstance().getMoods()) {
            log.debug(m.toString());
        }
    }
    @FXML public void btnExitAction() {   // Name im FXML File als Action Event angegeben
        log.debug("Exit Button Clicked");
        getSceneManager().exitApplication();
    }
}
