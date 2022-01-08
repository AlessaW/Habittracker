package org.jap.controller;

import javafx.scene.Scene;
import org.jap.view.SceneManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GenericController {
    private static final Logger log = LogManager.getLogger(GenericController.class);
    
    // Variables
    private SceneManager sceneManager;
    
    private Scene scene;
    
    // Methods
    public void initController(SceneManager sceneManager, Scene scene) {
        this.sceneManager = sceneManager;
        this.scene = scene;
    }
    
    public void activate() { // Initial method after scene switch
        log.debug(this.getClass().getSimpleName()+" Activated");
    }
    public void deactivate() { // Last method before scene switches
        log.debug(this.getClass().getSimpleName()+" Deactivated");
    }
    
    protected SceneManager getSceneManager() {
        return sceneManager;
    }
    
    public Scene getScene() {
        return scene;
    }
}
