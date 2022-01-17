package org.jap.controller;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.chart.LineChart;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jap.model.mood.MoodData;
import org.jap.model.mood.MoodManager;
import org.jap.view.SceneManager;


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

    private MoodData mood;
    private StatsStates statState;
    private final static StatsStates DEFAULT_STATS_STATE = StatsStates.RBTN_COMBINED;

    private ObservableList<MoodData> moodDataList; //Todo: maybe not private? -> MoodManager könnte drauf zugreifen?

    // Enum declaration
    /**
     * WindowState enum
     * Holds the path location for the fxml files
     */
    public enum StatsStates {
        RBTN_MOOD,
        RBTN_ACTIVATION,
        RBTN_COMBINED
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


        ObservableList<XYChart.Series<String, Integer>> lineChartData = lineChart.getData();
        String time = mood.getTimeStamp().toString();
        Integer value = mood.getMoodValue();
        for (MoodData mood : moodDataList) {
           // lineChartData.add(new XYChart.Series<>(time, value));
           // lineChart.get
        }
        updateChart();
    }


    @FXML
    public void rBtnMoodAction() {
        log.debug("Radiobutton Mood clicked");
        statState = StatsStates.RBTN_MOOD;
    }

    @FXML
    public void rBtnActivationAction() {
        log.debug("Radiobutton Activation clicked");
        statState = StatsStates.RBTN_ACTIVATION;
    }

    @FXML
    public void rBtnCombinedAction() {
        log.debug("Radiobutton Combined clicked");
        statState = StatsStates.RBTN_COMBINED;
    }

    private void updateChart(){
        moodDataList.clear();
        moodDataList.addAll(MoodManager.getInstance().getMoods());
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
