package org.jap.controller;

import javafx.collections.ListChangeListener;
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
import java.util.*;

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
    ItemTemplate currentTemplate = ItemTemplate.values()[0];
    ListChangeListener<MoodData> moodsChanged = change -> updateList();
    
    // FXML Fields
    @FXML private ListView<MoodData> livMoodList;
    
    // configuration constants
    private static final ItemTemplate DEFAULT_ITEM_TEMPLATE = ItemTemplate.PETER_TEST;
    
    // Inner Classes
    public enum ItemTemplate {
        HBOX("/fxml/hBoxListViewTemplate.fxml"),
//        VBOX("/fxml/vBox.fxml"),
        PETER_TEST("/fxml/peterTestListViewTemplate.fxml"),
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
        
        setTemplate(DEFAULT_ITEM_TEMPLATE);
        
        updateList();
    }
    
    /**
     * called when this controller is activated
     */
    @Override
    public void activate() {
        super.activate();
        updateList();
        MoodManager.getInstance().getMoods().addListener(moodsChanged);
    }
    
    /**
     * called when this controller is deactivated
     */
    @Override
    public void deactivate() {
        super.deactivate();
        MoodManager.getInstance().getMoods().removeListener(moodsChanged);
    }
    
    /**
     * switches the scene back to the main menu
     */
    @FXML
    public void btnBackAction() {
        log.debug("Back Button Clicked");
        getSceneManager().returnToPreviousValidScene();
    }
    
    /**
     * updates the observable list with the new data
     */
    public void updateList() {
        livMoodList.getItems().clear();
        livMoodList.setFixedCellSize(40);
        List<MoodData> moods = MoodManager.getInstance().getMoods().stream()
                .sorted(Comparator.comparing(MoodData::getTimeStamp))
//                .limit(500) // maybe we should add a limit...
                .toList();
        livMoodList.getItems().addAll(moods);
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
