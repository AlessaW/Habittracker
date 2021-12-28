package org.jap.view;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadException;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jap.controller.GenericController;

public class SceneManager {
    private static final Logger log = LogManager.getLogger(SceneManager.class);
    
    // Variables
    private final Stage rootStage;
    
    private final Scene[] scenes = new Scene[States.values().length];
    private final GenericController[] controllers = new GenericController[States.values().length];
    
    private States currentState;
    
    // Enum declaration
    /**
     * WindowState enum
     * Holds the path location for the fxml files
     */
    public enum States {
        STARTUP_MENU("/fxml/startupMenu.fxml"),
        CREATE_MOOD("/fxml/createMood.fxml"),
//        EDIT_MOOD("/fxml/editMood.fxml"),
//        VIEW_STATS("/fxml/viewStats.fxml"),
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
        this(stage, States.STARTUP_MENU); // Default window state
    }
    
    /**
     * Sets up the stage and the scene for the specified window state
     * @param stage the stage to set the scene up in
     * @param state the window state to load the scene for
     */
    public SceneManager(Stage stage, States state) {
        rootStage = stage;
        
        switchScene(state);
    }
    
    // Methods
    /**
     * Loads the scene from file and overwrites the old scene of it's state
     * @param state the window state to load the scene for
     */
    public void loadScene(States state) {
        try {
            final FXMLLoader loader = new FXMLLoader(getClass().getResource(state.url));
            final Pane loadedPane = loader.load();
            scenes[state.ordinal()] = new Scene(loadedPane);
            controllers[state.ordinal()] = loader.getController();
            controllers[state.ordinal()].setSceneManager(this); // give the controller a reference to this instance of the SceneManager, so they can switch scenes
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
    public void softLoadScene(States state) {
        if (scenes[state.ordinal()] == null) {  // Lazy scene Loading
            loadScene(state);
        }
    }
    
    /**
     * Switch the scene at the stage for the specified window state
     * @param state the window state to load the scene for
     */
    public void switchScene(States state) {
        softLoadScene(state);
        if(currentState!=null)
            controllers[currentState.ordinal()].deactivate();
        currentState = state;
        rootStage.setScene(scenes[currentState.ordinal()]);
        controllers[currentState.ordinal()].activate();
    }
    
    /**
     * Closes the Application
     * perhaps does some cleanup and necessary stuff first
     */
    public void exitApplication() {
        // do some important stuff here, if needed
        controllers[currentState.ordinal()].deactivate();
        
        rootStage.close();
    }
}
