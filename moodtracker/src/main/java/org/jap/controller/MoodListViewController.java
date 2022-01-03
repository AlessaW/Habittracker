package org.jap.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.util.Callback;
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
        if (moodDataList.size()<=0)
            moodDataList.add(null);
        
        livMoodList.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(MoodData mood, boolean empty) {
                super.updateItem(mood, empty);
                
                if (mood == null || empty) {
                    setText("Hier könnte ihre Werbung stehen!");
                } else {
                    // Here we can build the layout we want for each ListCell. Let's use a HBox as our root.
                    HBox root = new HBox(10);
                    root.setAlignment(Pos.CENTER_LEFT);
                    root.setPadding(new Insets(5, 10, 5, 10));
    
                    // Within the root, we'll show the username on the left and our two buttons to the right
                    root.getChildren().add(new Label(mood.getName()));
    
                    // I'll add another Region here to expand, pushing the buttons to the right
                    Region region = new Region();
                    HBox.setHgrow(region, Priority.ALWAYS);
                    root.getChildren().add(region);
    
                    // Now for our buttons
                    Button btnEdit = new Button("Edit");
                    btnEdit.setOnAction(event -> {
                        // Code to add friend
                        log.debug("Edit Mood ID: " + mood.getMoodID());
                    });
                    Button btnDelete = new Button("Delete");
                    btnDelete.setOnAction(event -> {
                        // Code to remove friend
                        log.debug("Delete Mood ID " + mood.getMoodID());
                    });
                    
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
                    
                    root.getChildren().addAll(btnEdit, btnDelete);
    
                    // Finally, set our cell to display the root HBox
                    setText(null);
                    setGraphic(root);
                }
            }
        });
    }
}
