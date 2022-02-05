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
    /**
     * called when this controller is activated
     */
    @Override
    public void activate() {
        super.activate();
    }
    
    /**
     * called when this controller is deactivated
     */
    @Override
    public void deactivate() {
        super.deactivate();
    }
    
    /**
     * switches the scene to the add mood view
     */
    @FXML public void btnAddMoodAction() {   // Name im FXML File als Action Event angegeben
        log.debug("AddMood Button Clicked");
        getSceneManager().switchScene(SceneManager.States.CREATE_MOOD);
    }
    
    /**
     * switches the scene to the edit view
     * <br> for debug purposes: switches the scene to the list view
     */
    @FXML public void btnEditMoodAction() {   // Name im FXML File als Action Event angegeben
        log.debug("EditMood Button Clicked");
        // Todo: important stuff
        
        getSceneManager().switchScene(SceneManager.States.MOOD_LIST_VIEW);
    }
    
    /**
     * switches the scene to the stats view
     * <br> for debug purposes: logs all moods
     */
    @FXML public void btnViewStatsAction() {   // Name im FXML File als Action Event angegeben
        log.debug("ViewStats Button Clicked");
        getSceneManager().switchScene(SceneManager.States.MOOD_STATS_VIEW);
        
      /*  log.debug("Saved "+MoodManager.getInstance().getMoods().size()+" Moods: ");
        for (MoodData m : MoodManager.getInstance().getMoods()) {
            log.debug(m.toString());
        }*/
    }
    
    /**
     * exits the application
     */
    @FXML public void btnExitAction() {   // Name im FXML File als Action Event angegeben
        log.debug("Exit Button Clicked");
        getSceneManager().exitApplication();
    }
}
