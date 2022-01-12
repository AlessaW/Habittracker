package org.jap.controller;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
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
    
    // Variables
    @FXML private TextField txfName;
    @FXML private TextField txfDescription;
    @FXML private Slider sliMood;
    @FXML private Slider sliActivation;
    @FXML private DatePicker dtpDate;
    @FXML private Spinner<Integer> spnHour;
    @FXML private Spinner<Integer> spnMinute;
    
    // Methods
    @Override
    public void activate() {
        super.activate();
        resetInput();
    }
    @Override
    public void deactivate() {
        super.deactivate();
    }
    
    private void resetInput() {
        txfName.setText("");
        txfDescription.setText("");
        sliMood.setValue(0);
        sliActivation.setValue(0);
        dtpDate.setValue(LocalDate.now());
        spnHour.getValueFactory().setValue(LocalTime.now().getHour());
        spnMinute.getValueFactory().setValue(LocalTime.now().getMinute());
    }
    
    @FXML public void btnOkAction() {
        log.debug("Ok Button Clicked");
        
        String name = txfName.getText();
        String description = txfDescription.getText();
        int moodValue = Math.round((float) sliMood.getValue());
        int activation = Math.round((float) sliActivation.getValue());
        LocalDate date = LocalDate.now();
        if (dtpDate.getValue() != null) date = dtpDate.getValue();
        LocalDateTime dateTime = date.atTime(spnHour.getValue(), spnMinute.getValue());
        
        log.debug("User Input:");
        log.debug("   Name: "+name);
        log.debug("   Description: "+description);
        log.debug("   Mood: "+moodValue);
        log.debug("   Activation: "+activation);
        log.debug("   DateTime: "+dateTime.atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL)));
        
        // Todo: interaction with the Model
        MoodData createdMood = MoodManager.getInstance().createMood(name,description,dateTime,activation,moodValue);
        MoodManager.getInstance().addMood(createdMood);
        log.debug("Created mood with MoodID: "+createdMood.getMoodID());
        log.debug("There are now "+MoodManager.getInstance().getMoods().size()+" moods saved");
        
        getSceneManager().switchScene(SceneManager.States.STARTUP_MENU);
    }
    
    @FXML public void btnCancelAction() {
        log.debug("Cancel Button Clicked");
        getSceneManager().switchScene(SceneManager.States.STARTUP_MENU);
    }
}
