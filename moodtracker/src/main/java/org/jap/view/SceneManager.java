package org.jap.view;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jap.controller.CreateMoodMenuController;
import org.jap.controller.GenericController;
import org.jap.controller.MenuBarController;
import org.jap.model.mood.MoodData;

public class SceneManager {
    private static final Logger log = LogManager.getLogger(SceneManager.class);
    
    // Variables
    private final Stage rootStage;
    
    private final GenericController[] controllers = new GenericController[States.values().length];
    private MenuBarController menuBarController;
    
    private States currentState;
    private final ArrayList<States> statesHistory = new ArrayList<>();
    
    // configuration constants
    public final static States DEFAULT_WINDOW_STATE = States.STARTUP_MENU;
    public final static int HISTORY_LIMIT = 200;
    
    // Enum declaration
    /**
     * WindowState enum
     * Holds the path location for the fxml files
     */
    public enum States {
        STARTUP_MENU("/fxml/startupMenu.fxml"),
        CREATE_MOOD("/fxml/createMood.fxml"),
        MOOD_LIST_VIEW("/fxml/moodListView.fxml"),
        EDIT_MOOD("/fxml/createMood.fxml"),
        MOOD_STATS_VIEW("/fxml/moodStatsView.fxml"),
        ;
        
        public final String url;
        
        States(String url) {
            this.url = url;
        }
    }
    
    // Constructor
    /**
     * Sets up the stage and the scene for the default window state
     * @param stage the stage to set the scene up in
     */
    public SceneManager(Stage stage) {
        this(stage, DEFAULT_WINDOW_STATE); // Default window state
    }
    
    /**
     * Sets up the stage and the scene for the specified window state
     * @param stage the stage to set the scene up in
     * @param state the window state to load the scene for
     */
    public SceneManager(Stage stage, States state) {
        rootStage = stage;
        
        // load the Menu Bar
        try {
            final FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/rootMenu.fxml"));
            final Parent loadedPane = loader.load();
            final Scene scene = new Scene(loadedPane);
            menuBarController = loader.getController();
            menuBarController.initController(this,loadedPane);
            rootStage.setScene(scene);
            menuBarController.activate();
        }
        catch (LoadException e) {
            log.error("Load Exception: " + e.getMessage());
        }
        catch (IOException e) {
            log.error("IO Exception: " + e.getMessage());
        }
        
        switchScene(state);
    }
    
    // Methods
    /**
     * Loads the scene from file and overwrites the old scene of it's state
     * @param state the window state to load the scene for
     */
    private void loadScene(States state) {
        try {
            final FXMLLoader loader = new FXMLLoader(getClass().getResource(state.url));
            final Parent loadedPane = loader.load();
            controllers[state.ordinal()] = loader.getController();
            // give the controller a reference to this instance of the SceneManager, so they can switch scenes; and store the scene in the controller
            controllers[state.ordinal()].initController(this,loadedPane);
        }
        catch (LoadException e) {
            log.error("Load Exception: " + e.getMessage());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Loads the scene only if it isn't yet loaded
     * @param state the window state to load the scene for
     */
    private void softLoadScene(States state) {
        if (controllers[state.ordinal()] == null) {  // Lazy scene Loading
            loadScene(state);
            log.trace("Loaded: "+state);
        } else
            log.trace(state+" is already loaded");
    }
    
    /**
     * Switch the scene at the stage for the specified window state
     * @param state the window state to load the scene for
     */
    public void switchScene(States state) {
        if (state != currentState) {
            statesHistory.add(state);
            if (statesHistory.size() > HISTORY_LIMIT) {
                statesHistory.remove(0);
            }
            softLoadScene(state);
            if (currentState != null)
                getCurrentController().deactivate();
            currentState = state;
            menuBarController.setScene(getCurrentController().getScene(), state);
            getCurrentController().activate();
            debugStatesHistory();
        } else {
            log.debug("Scene is already presented");
        }
    }
    
    private void debugStatesHistory() {
        log.debug("States History: ");
        for (int i = 0; i<statesHistory.size(); i++) {
            log.debug("["+i+"] "+statesHistory.get(i));
        }
    }
    
    public void returnToPreviousValidScene() {
        States previousState = null;
        int historyStep = 0;
    
        for (int i = statesHistory.size()-1; i>=0; i--) {
            if (!statesHistory.get(i).equals(currentState) && isValidState(statesHistory.get(i)))
            {
                historyStep = i;
                previousState = statesHistory.get(i);
                break;
            }
        }
        
        if (previousState != null && previousState != currentState) {
            statesHistory.subList(historyStep, statesHistory.size()).clear();
            switchScene(previousState);
        }
    }
    
    /**
     * @return the last valid state to return to
     * <br> a state is valid if it makes sense to return to it from another state
     * <br> e.g. EDIT_MOOD is invalid. if we return to it without setting a mood, it is unclear which mood to edit
     * <br> instead of the invalid state OR if it is equal to the current state, it tries the next previous state
     * <br> if no valid state is found it returns the default window state
     */
    public States getPreviousValidScene() {
        States previousState = DEFAULT_WINDOW_STATE;
        
        for (int i = statesHistory.size()-1; i>0; i--) {
            if (!statesHistory.get(i).equals(currentState) && isValidState(statesHistory.get(i)))
            {
                previousState = statesHistory.get(i);
                break;
            }
        }
        
        return previousState;
    }
    
    /**
     * Closes the Application
     * perhaps does some cleanup and necessary stuff first
     */
    public void exitApplication() {
        // do some important stuff here, if needed
        getCurrentController().deactivate();
        menuBarController.deactivate();
        
        rootStage.close();
    }
    
    /**
     * Method to give a mood to the controller
     * @param mood the mood to be edited
     */
    public void editMood(MoodData mood) {
        switchScene(States.EDIT_MOOD);
        ((CreateMoodMenuController) controllers[States.EDIT_MOOD.ordinal()]).preloadMood(mood);
    }
    
    /**
     * @param state the state to validate
     * @return true if the state is a valid state to return to
     * <br> a state is valid if it makes sense to return to it from another state
     * <br> e.g. EDIT_MOOD is invalid. if we return to it without setting a mood, it is unclear which mood to edit
     */
    private boolean isValidState(States state) {
        return ( // invalid states to not return to
//            state != States.CREATE_MOOD && // may be valid because it gets reset every time its activated
            state != States.EDIT_MOOD
        );
    }
    
    /**
     * @return the controller for the currently active scene
     */
    private GenericController getCurrentController() {
        return controllers[currentState.ordinal()];
    }
}
