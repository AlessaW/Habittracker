package org.jap.controller;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadException;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import org.jap.model.mood.MoodData;
import org.jap.model.mood.MoodManager;
import org.jap.view.SceneManager;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
    Created by Peter
*/

/**
 * The Controller Class for the Mood List View
 */
public class MoodListViewController extends GenericController{
    private static final Logger log = LogManager.getLogger(MoodListViewController.class);
    
    // Variables
    ObservableList<MoodData> moodDataList;
    ItemTemplate currentTemplate = ItemTemplate.values()[0];
    
    // FXML Fields
    @FXML private ListView<MoodData> livMoodList;
    
    // enum
    public enum ItemTemplate {
        PETER_TEST("/fxml/peterTestListViewTemplate.fxml"),
        HBOX("/fxml/hBoxListViewTemplate.fxml"),
//        VBOX("/fxml/vBox.fxml"),
        ;
    
        public final String url;
    
        ItemTemplate(String url) {
            this.url = url;
        }
    }
    
    // Methods
    /**
     * Controller Initialization
     * @param sceneManager scene Manager for scene switching
     * @param scene        scene is stored in the controller
     */
    @Override
    public void initController(SceneManager sceneManager, Parent scene) {
        super.initController(sceneManager, scene);
        
        setTemplate(ItemTemplate.PETER_TEST);
        
        moodDataList = livMoodList.getItems();
        updateList();
    }
    
    /**
     * called when this controller is activated
     */
    @Override
    public void activate() {
        super.activate();
        updateList();
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
     * updates the observable list with the new data
     */
    public void updateList() {
        moodDataList.clear();
        moodDataList.addAll(MoodManager.getInstance().getMoods());
    }
    
    private void setTemplate(ItemTemplate t) {
        currentTemplate = t;
    
        livMoodList.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(MoodData mood, boolean empty) {
                super.updateItem(mood, empty);
            
                if (mood == null || empty) {
//                    setText("Hier könnte ihre Werbung stehen!");
                    setText(null);
                    setGraphic(null);
                } else {
                    final FXMLLoader loader = new FXMLLoader(getClass().getResource(currentTemplate.url));
                    Pane root = new Pane();
                    try {
                        root = loader.load();
                        MoodListItemController controller = loader.getController();
                        controller.initController(getSceneManager(), MoodListViewController.this);
                        controller.setMood(mood);
                    } catch (LoadException e) {
                        log.error("Load Exception: " + e.getMessage());
                    } catch (IOException e) {
                        log.error("IO Exception: " + e.getMessage());
                    }
                
                    setText(null);
                    setGraphic(root);
                }
            }
        });
    }
}
