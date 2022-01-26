package org.jap.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jap.model.mood.MoodManager;
import org.jap.view.SceneManager;

import java.util.Optional;

/*
    Created by Peter
*/

/**
 *
 */
public class MenuBarController extends GenericController{
    private static final Logger log = LogManager.getLogger(MenuBarController.class);
    
    // Variables
    @FXML private Pane anpSceneRoot;
    
    // Methods
    public void setScene(Node scene, SceneManager.States state) {
        ObservableList<Node> list = anpSceneRoot.getChildren();
        list.clear();
        list.add(scene);
        log.trace("set Scene "+state+", list size now: "+list.size());
    }
    
    // FXML Event Handler
    @FXML
    public void mniGeneralMainMenu() {
        log.trace("Clicked Menu Item: General > Main Menu");
        getSceneManager().switchScene(SceneManager.States.STARTUP_MENU);
    }
    
    @FXML
    public void mniGeneralSettings() {
        log.trace("Clicked Menu Item: General > Settings");
        //Todo
    }
    
    @FXML
    public void mniGeneralQuit() {
        log.trace("Clicked Menu Item: General > Quit");
        getSceneManager().exitApplication();
    }
    
    @FXML
    public void mniAddCustom() {
        log.trace("Clicked Menu Item: Add > Custom");
        getSceneManager().switchScene(SceneManager.States.CREATE_MOOD);
    }
    
    @FXML
    public void mniAddPredefined() {
        log.trace("Clicked Menu Item: Add > Predefined");
        //Todo
    }
    
    @FXML
    public void mniEditEditMood() {
        log.trace("Clicked Menu Item: Edit > Edit Mood");
        getSceneManager().switchScene(SceneManager.States.MOOD_LIST_VIEW);
    }
    
    @FXML
    public void mniEditDeleteMood() {
        log.trace("Clicked Menu Item: Edit > Delete Mood");
        getSceneManager().switchScene(SceneManager.States.MOOD_LIST_VIEW);
    }
    
    @FXML
    public void mniEditDeleteAll() {
        log.trace("Clicked Menu Item: Edit > Delete All");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete ALL Moods?");
        Optional<ButtonType> result = alert.showAndWait()
                .filter(response -> response == ButtonType.OK);
        if (result.isPresent()) {
            MoodManager.getInstance().deleteAllMoods();
        }
    }
    
    @FXML
    public void mniEditUndo() {
        log.trace("Clicked Menu Item: Edit > Undo");
        //Todo
    }
    
    @FXML
    public void mniViewsBack() {
        log.trace("Clicked Menu Item: General > Back");
        getSceneManager().returnToPreviousValidScene();
    }
    
    @FXML
    public void mniViewsStats() {
        log.trace("Clicked Menu Item: Views > Stats");
        //Todo
    }
    
    @FXML
    public void mniViewsList() {
        log.trace("Clicked Menu Item: Views > List");
        getSceneManager().switchScene(SceneManager.States.MOOD_LIST_VIEW);
    }
    
    @FXML
    public void mniViewsDebug() {
        log.trace("Clicked Menu Item: Views > List");
        getSceneManager().switchScene(SceneManager.States.DEBUG_VIEW);
    }
}
