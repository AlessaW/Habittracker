package org.jap.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.chart.LineChart;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jap.model.mood.MoodData;
import org.jap.model.mood.MoodManager;


/**
 * Controller for the Stats View
 * There are three types of view on the linegraph: only mood-level, only activation level and the combined view where the two values are
 * calculated together into a single graph
 *
 * Created by Jannika
*/

public class MoodStatsViewController extends GenericController{
    private static final Logger log = LogManager.getLogger(MoodStatsViewController.class);

    @FXML private RadioButton rBtnMood;
    @FXML private RadioButton rBtnActivation;
    @FXML private RadioButton rBtnCombined;
    @FXML private LineChart lineChart; //Todo: private observable List?

    private MoodData mood;
    private boolean editMode = false;


    // Methods
    /**
     * called when this controller is activated
     */
    @Override
    public void activate() {
        super.activate();
        //resetInput(); //Todo: hier muss Daten geladen werden
    }
    /**
     * called when this controller is deactivated
     */
    @Override
    public void deactivate() {
        super.deactivate();
    }


}
