package org.jap.controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.jap.model.mood.MoodData;
import org.jap.model.mood.MoodManager;
import org.jap.view.SceneManager;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
    Created by Peter
*/

/**
 *
 */
public class MoodListItemController {
    private static final Logger log = LogManager.getLogger(MoodListItemController.class);
    
    // Variables
    private SceneManager sceneManager;
    private MoodListViewController listController;
    
    private MoodData mood;
    
    // FXML Fields
    @FXML private Parent root;
    @FXML private Label lblDate;
    @FXML private Label lblName;
    @FXML private Label lblDescription;
    @FXML private Button btnEdit;
    @FXML private Button btnDelete;
    
    // Methods
    public void initController(SceneManager sceneManager, MoodListViewController listController) {
        this.sceneManager = sceneManager;
        this.listController = listController;
    
        btnEdit.setVisible(false);
        btnDelete.setVisible(false);
        root.setOnMouseEntered(mouseEvent -> {
            btnEdit.setVisible(true);
            btnDelete.setVisible(true);
        });
        root.setOnMouseExited(mouseEvent -> {
            btnEdit.setVisible(false);
            btnDelete.setVisible(false);
        });
    }
    
    public void setMood(MoodData mood) {
        this.mood = mood;
        lblDate.setText(mood.getTimeStamp().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
        lblName.setText(mood.getName());
        lblDescription.setText(mood.getDescription());
    }
    
    @FXML
    public void btnEditAction() {
        log.debug("Button Edit Pressed on ID: "+mood.getMoodID());
        sceneManager.editMood(mood); // Send a Copy of mood to Edit
        listController.updateList();
    }
    
    @FXML
    public void btnDeleteAction() {
        log.debug("Button Delete Pressed on ID: "+mood.getMoodID());
        MoodManager.getInstance().deleteMood(mood);
        listController.updateList();
    }
}
