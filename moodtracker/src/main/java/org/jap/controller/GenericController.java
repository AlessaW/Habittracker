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
    
    public void activate() { // Initial method after scene switch
        String[] className = this.getClass().getName().split("\\.");
        log.debug(className[className.length-1]+" Activated");
    }
    public void deactivate() { // Last method before scene switches
        String[] className = this.getClass().getName().split("\\.");
        log.debug(className[className.length-1]+" Deactivated");
    }
    
    protected SceneManager getSceneManager() {
        return sceneManager;
    }
}
