package org.jap.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jap.model.mood.MoodData;
import org.jap.model.mood.MoodManager;
import org.jap.view.SceneManager;

import java.util.List;

/*
    Created by Peter
*/

/**
 * The Controller Class for the Mood List View
 */
public class MoodListViewController extends GenericController{
    private static final Logger log = LogManager.getLogger(MoodListViewController.class);
    
    // Variables
    List<MoodData> originalList;
    ObservableList<MoodData> moodDataList;
    
    // FXML Fields
    @FXML private ListView<MoodData> livMoodList;
    
    // Methods
    /**
     * called when this controller is activated
     */
    @Override
    public void activate() {
        super.activate();
        load();
    }
    
    /**
     * called when this controller is deactivated
     */
    @Override
    public void deactivate() {
        super.deactivate();
    }
    
    /**
     * switches the scene back to the main menu
     */
    @FXML
    public void btnBackAction() {
        log.debug("Back Button Clicked");
        getSceneManager().switchScene(SceneManager.States.STARTUP_MENU);
    }
    
    /**
     * Loads the data to the list view
     */
    private void load() {
        updateList();
        livMoodList.setItems(moodDataList);
    }
    
    /**
     * updates the observable list with the new data
     */
    private void updateList() {
        originalList = MoodManager.getInstance().getMoods();
        moodDataList = FXCollections.observableArrayList(originalList);
    }
}
