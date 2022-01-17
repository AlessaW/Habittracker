package org.jap.controller;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.RadioButton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jap.model.mood.MoodData;
import org.jap.model.mood.MoodManager;
import org.jap.view.SceneManager;

import java.time.LocalDateTime;
import java.util.List;


/**
 * Controller for the Stats View
 * There are three types of view on the linegraph: only mood-level, only activation level and the combined view where the two values are
 * calculated together into a single graph
 *
 * Created by Jannika
*/

public class MoodStatsViewController extends GenericController{
    private static final Logger log = LogManager.getLogger(MoodStatsViewController.class);

    @FXML private RadioButton rBtnMood; //todo: Mehrfachauswahl ermöglich! -> Checkbox
    @FXML private RadioButton rBtnActivation;
    @FXML private RadioButton rBtnCombined;
    @FXML private LineChart<String, Integer> lineChart; //Todo: private observable List?
    @FXML private CategoryAxis xAxis;
    @FXML private NumberAxis yAxis;

    private StatsStates statState;
    private final static StatsStates DEFAULT_STATS_STATE = StatsStates.RBTN_COMBINED;

    private List<MoodData> moodDataList; //Todo: maybe not private? -> MoodManager könnte drauf zugreifen?

    private XYChart.Series<String, Integer> moodValueSeries;
    private XYChart.Series<String, Integer> activationSeries;
    private XYChart.Series<String, Integer> combinedSeries;




    // Enum declaration
    /**
     * WindowState enum
     * Holds the path location for the fxml files
     */
    public enum StatsStates {
        RBTN_MOOD,
        RBTN_ACTIVATION,
        RBTN_COMBINED,
        TEST_STATE
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
        moodDataList = MoodManager.getInstance().getMoods(); //Kopie der Moodliste
        makeActivationSeries();
        makeCombinedSeries();
        makeMoodValueSeries();


/*        switch(statState){
            case RBTN_MOOD -> rBtnMood.setSelected(true);
            case RBTN_ACTIVATION -> rBtnActivation.setSelected(false);
            case RBTN_COMBINED -> rBtnCombined.setSelected(false);
        }*/


        lineChart.getData().addAll(combinedSeries, moodValueSeries,activationSeries);
        moodValueSeries = new XYChart.Series<>();

        moodValueSeries.getData().add(new XYChart.Data<>("Jul", 1));
        moodValueSeries.getData().add(new XYChart.Data<>("Aug", 5));
        updateChart();
    }

    @FXML
    public void rBtnMoodAction() { //Todo: doch besser checkbox?
        log.debug("Radiobutton Mood clicked");
        statState = StatsStates.RBTN_MOOD;
        lineChart.getData().add(moodValueSeries);
    }

    @FXML
    public void rBtnActivationAction() {
        log.debug("Radiobutton Activation clicked");
        statState = StatsStates.RBTN_ACTIVATION;
        lineChart.getData().add(activationSeries);
        log.debug("Data Added");
    }

    @FXML
    public void rBtnCombinedAction() {
        log.debug("Radiobutton Combined clicked");
        statState = StatsStates.RBTN_COMBINED;
        lineChart.getData().add(combinedSeries);
    }

    //Display Data Methods

    private void updateChart(){
       //Liste aus Linien (Series), die dargestellt werden

    }

    private void makeMoodValueSeries(){
        log.debug("MoodValueSeries made");
        moodValueSeries = new XYChart.Series<>();
        moodValueSeries.setName("Moods");

        for (MoodData mood : moodDataList) {
            String time = mood.getTimeStamp().toString();
            Integer value = mood.getMoodValue();
            moodValueSeries.getData().add(new XYChart.Data<>(time, value));
            log.debug("Mood");
        }
    }

    private void makeActivationSeries(){
        activationSeries = new XYChart.Series<>();
        activationSeries.setName("Activation");
        log.debug("Activation made");

        for (MoodData mood : moodDataList) {
            String time = mood.getTimeStamp().toString();
            Integer activation = mood.getActivityLevel();
            activationSeries.getData().add(new XYChart.Data<>(time, activation));
        }
    }

    private void makeCombinedSeries(){
        combinedSeries = new XYChart.Series<>();
        combinedSeries.setName("Combined");

        for (MoodData mood : moodDataList) {
            String time = mood.getTimeStamp().toString();
            Integer combined = mood.getActivityLevel() + mood.getMoodValue(); //Todo: Sinnhaft, wie es berechnet werd?
            combinedSeries.getData().add(new XYChart.Data<>(time, combined));
        }
    }



    /**
     * called when this controller is activated
     */
    @Override
    public void activate() {
        super.activate();
        //resetInput(); //Todo: hier muss Daten geladen werden
        //todo: reset date von der Zeitauswahl?
        //todo: filter zurücksetzen?
    }
    /**
     * called when this controller is deactivated
     */
    @Override
    public void deactivate() {
        super.deactivate();
    }


}
