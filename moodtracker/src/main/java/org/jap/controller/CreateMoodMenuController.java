package org.jap.controller;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    @FXML public void btnOkAction() {
        // Things are happening now
        log.debug("Ok Button Clicked");
        log.debug("User Input:" +
                "\n  Name: "+txfName.getText()+
                "\n  Description: "+txfDescription.getText()+
                "\n  Mood: "+sliMood.getValue()+
                "\n  Activation: "+sliActivation.getValue()+
                "\n  Date: "+dtpDate.getValue().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
        // Todo: stuff! you now?
    }
}
