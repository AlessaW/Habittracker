package org.jap.controller;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
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
    }
    
    @FXML public void btnOkAction() {
        log.debug("Ok Button Clicked");
        
        String name = txfName.getText();
        String description = txfDescription.getText();
        int moodValue = Math.round((float) sliMood.getValue());
        int activation = Math.round((float) sliActivation.getValue());
        LocalDate date;
        if (dtpDate.getValue() != null) date = dtpDate.getValue();
        else date = LocalDate.now();
        
        log.debug("User Input:" +
                "\n  Name: "+name+
                "\n  Description: "+description+
                "\n  Mood: "+moodValue+
                "\n  Activation: "+activation+
                "\n  Date: "+date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
        // Todo: interaction with the Model
    }
}
