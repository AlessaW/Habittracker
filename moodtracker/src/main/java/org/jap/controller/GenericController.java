package org.jap.controller;

import org.jap.view.SceneManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GenericController {
    private static final Logger log = LogManager.getLogger(GenericController.class);
    
    // Variables
    private SceneManager sceneManager;
    
    // Methods
    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }
    
    protected SceneManager getSceneManager() {
        return sceneManager;
    }
}
