package org.jap.controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.chart.*;
import javafx.scene.control.CheckBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jap.model.mood.MoodData;
import org.jap.model.mood.MoodManager;
import org.jap.view.SceneManager;

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

    @FXML private CheckBox cbMood;
    @FXML private CheckBox cbActivation;
    @FXML private CheckBox cbCombined;
    @FXML private LineChart<String, Integer> lineChart; //Todo: private observable List?
    @FXML private CategoryAxis xAxis;
    @FXML private NumberAxis yAxis;


    private StatsStates statState;
    private final static StatsStates DEFAULT_STATS_STATE = StatsStates.CB_COMBINED;

     //Todo: maybe not private? -> MoodManager könnte drauf zugreifen?

    private XYChart.Series<String, Integer> moodValueSeries;
    private XYChart.Series<String, Integer> activationSeries;
    private XYChart.Series<String, Integer> combinedSeries;

    public List<MoodData> moodDataList;

    private boolean moodVis = false;
    private boolean activationVis = false;
    private boolean combinedVis = false;




    // Enum declaration
    /**
     * WindowState enum
     * Holds the path location for the fxml files
     */
    public enum StatsStates {
        CB_MOOD,
        CB_ACTIVATION,
        CB_COMBINED,
        TEST_STATE
    }


    // Methods
    /**
     * Controller Initialization
     * @param sceneManager scene Manager for scene switching
     * @param scene        scene is stored in the controller
     */
    @Override
    public void initController(SceneManager sceneManager, Parent scene) { //Todo: init schön machen
        super.initController(sceneManager, scene);                          //Todo: sinnvolle Reihenfolge machen
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
    public void cbMoodAction() { //Todo: doch besser checkbox?
        log.debug("Checkbox Mood clicked");
        statState = StatsStates.CB_MOOD;
        lineChart.getData().add(moodValueSeries);
    }

    @FXML
    public void cbActivationAction() {
        log.debug("Checkbox Activation clicked");
        statState = StatsStates.CB_ACTIVATION;
        lineChart.getData().add(activationSeries);
        log.debug("Data Added");
    }

    @FXML
    public void cbCombinedAction() {
        log.debug("Checkbox Combined clicked");
        statState = StatsStates.CB_COMBINED;
        lineChart.getData().add(combinedSeries);
        log.debug("Data for Combined Added");
        setSeriesVisible(combinedSeries, true);
        log.debug("Combined set visible");
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
            Integer combined = (Math.abs(mood.getActivityLevel()) + Math.abs(mood.getMoodValue()))/2; //Todo: Sinnhaft, wie es berechnet wird?
            combinedSeries.getData().add(new XYChart.Data<>(time, combined));
        }
    }

    private void setSeriesVisible(XYChart.Series<?,?> s, boolean visible) {
        s.getNode().setVisible(visible); // Toggle visibility of line
        for (XYChart.Data<?,?> d : s.getData()) {
            if (d.getNode() != null) {
                d.getNode().setVisible(visible); // Toggle visibility of every node in the series
            }
        }
    }

    private void setDate(){
        //Todo: set current date according to
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

        switch (statState) {
            case CB_MOOD -> {
                cbMood.setSelected(true);
                cbMoodAction();
            }
            case CB_ACTIVATION -> {
                cbActivation.setSelected(true);
                cbActivationAction();
            }
            case CB_COMBINED -> {
                cbCombined.setSelected(true);
                cbActivationAction();
            }
        }
    }
    /**
     * called when this controller is deactivated
     */
    @Override
    public void deactivate(){
        super.deactivate();
    }


}
