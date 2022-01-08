package org.jap.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jap.model.mood.MoodData;
import org.jap.model.mood.MoodManager;
import org.jap.view.SceneManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/*
    Created by Peter
*/

/**
 * The Controller Class for the Create Mood View
 */
public class CreateMoodMenuController extends GenericController {
    private static final Logger log = LogManager.getLogger(CreateMoodMenuController.class);
    
    // FXML Fields
    @FXML private TextField txfName;
    @FXML private TextField txfDescription;
    @FXML private Slider sliMood;
    @FXML private Slider sliActivation;
    @FXML private DatePicker dtpDate;
    @FXML private Spinner<Integer> spnHour;
    @FXML private Spinner<Integer> spnMinute;
    @FXML private Button btnOk;
    @FXML private Button btnCancel;
    
    private MoodData mood;
    
    // Methods
    /**
     * called when this controller is activated
     */
    @Override
    public void activate() {
        super.activate();
        resetInput();
    }
    /**
     * called when this controller is deactivated
     */
    @Override
    public void deactivate() {
        super.deactivate();
    }
    
    /**
     * resets the input of the gui controls
     */
    private void resetInput() {
        txfName.setText("");
        txfDescription.setText("");
        sliMood.setMin(MoodData.MIN_MOODVALUE);
        sliMood.setMax(MoodData.MAX_MOODVALUE);
        sliMood.setValue(MoodData.DEFAULT_MOODVALUE);
        sliActivation.setMin(MoodData.MIN_ACTIVITYLEVEL);
        sliActivation.setMax(MoodData.MAX_ACTIVITYLEVEL);
        sliActivation.setValue(MoodData.DEFAULT_ACTIVITYLEVEL);
        dtpDate.setValue(LocalDate.now());
        spnHour.getValueFactory().setValue(LocalTime.now().getHour());
        spnMinute.getValueFactory().setValue(LocalTime.now().getMinute());
        
//        btnOk.setText("Create");
    }
    
    public void preloadMood(MoodData m) {
        mood = m;
    
        txfName.setText(mood.getName());
        txfDescription.setText(mood.getDescription());
        sliMood.setValue(mood.getMoodValue());
        sliActivation.setValue(mood.getActivityLevel());
        dtpDate.setValue(mood.getTimeStamp().toLocalDate());
        spnHour.getValueFactory().setValue(mood.getTimeStamp().toLocalTime().getHour());
        spnMinute.getValueFactory().setValue(mood.getTimeStamp().toLocalTime().getMinute());
        
//        btnOk.setText("Change");
    }
    
    /**
     * Creates a new mood with the given inputs from the gui controls
     */
    @FXML public void btnOkAction() {
        log.debug("Ok Button Clicked");
        
        // Read all User Inputs
        String name = txfName.getText();
        String description = txfDescription.getText();
        int moodValue = Math.round((float) sliMood.getValue());
        int activation = Math.round((float) sliActivation.getValue());
        LocalDate date = LocalDate.now();
        if (dtpDate.getValue() != null) date = dtpDate.getValue();
        LocalDateTime dateTime = date.atTime(spnHour.getValue(), spnMinute.getValue());
        
        // Debug Log
        log.debug("User Input:");
        log.debug("   Name: "+name);
        log.debug("   Description: "+description);
        log.debug("   Mood: "+moodValue);
        log.debug("   Activation: "+activation);
        log.debug("   DateTime: "+dateTime.atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL)));
        
        // Interaction with the model
        if (mood == null) {
            MoodData createdMood = new MoodData(name, description, dateTime, activation, moodValue);
            MoodManager.getInstance().addMood(createdMood);
            log.debug("Created mood with MoodID: "+createdMood.getMoodID());
        } else {
            if (!mood.getName().equals(name))
                MoodManager.getInstance().changeName(mood,name);
            if (!mood.getDescription().equals(description))
                MoodManager.getInstance().changeDescription(mood,description);
            if (mood.getMoodValue() != moodValue)
                MoodManager.getInstance().changeMoodValue(mood,moodValue);
            if (mood.getActivityLevel() != activation)
                MoodManager.getInstance().changeActivityLevel(mood,activation);
            if (!mood.getTimeStamp().equals(dateTime))
                MoodManager.getInstance().changeTimeStamp(mood,dateTime);
            
            log.debug("Changed mood with MoodID: "+mood.getMoodID());
        }
        log.debug("There are now "+MoodManager.getInstance().getMoods().size()+" moods saved");
        
        // return to previous scene
        getSceneManager().switchScene(SceneManager.States.STARTUP_MENU);
    }
    
    /**
     * returns the user back to the main menu
     */
    @FXML public void btnCancelAction() {
        log.debug("Cancel Button Clicked");
        getSceneManager().switchScene(SceneManager.States.STARTUP_MENU);
    }
}
