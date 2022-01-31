package org.jap.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.CheckBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jap.model.datahandler.DataListProvider;
import org.jap.model.mood.MoodData;
import org.jap.model.mood.MoodManager;
import org.jap.view.App;
import org.jap.view.SceneManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 * Controller for the Stats View
 * There are three types of view on the linegraph: only mood-level, only activation level and the combined view where the two values are
 * calculated together into a single graph
 * //Todo: Day times noch einfügen
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
    @FXML private Button btnDay;
    @FXML private Button btnWeek;
    @FXML private Button btnMonth;
    @FXML private Button btnYear;
    @FXML private DatePicker dtpDateFrom;
    @FXML private DatePicker dtpDateTill;
    
    
    private TimeOption timeOption;

    private ArrayList<MoodData> timedList;
    private ObservableList<MoodData> moodDataList;

     //Todo: maybe not private? -> MoodManager könnte drauf zugreifen?

    private XYChart.Series<String, Integer> moodValueSeries;
    private XYChart.Series<String, Integer> activationSeries;
    private XYChart.Series<String, Integer> combinedSeries;


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

    public enum TimeOption{
        DAY,
        WEEK
    }


    // Methods
    /**
     * Controller Initialization
     * @param sceneManager scene Manager for scene switching
     * @param scene        scene is stored in the controller
     */
    @Override
    public void initController(SceneManager sceneManager, Parent scene) { //Todo: init schön machen
        super.initController(sceneManager, scene);//Todo: sinnvolle Reihenfolge machen
      //  timedList = new ArrayList<MoodData>(getWeeklyList());
     //   DataListProvider.moodDataList = MoodManager.getInstance().getMoods(); //Kopie der Moodliste
    
        updateChart();
        makeActivationSeries();
        makeCombinedSeries();
        makeMoodValueSeries();


/*        switch(statState){
            case RBTN_MOOD -> rBtnMood.setSelected(true);
            case RBTN_ACTIVATION -> rBtnActivation.setSelected(false);
            case RBTN_COMBINED -> rBtnCombined.setSelected(false);
        }*/

//        switch(timeOption){
//
//        }


        lineChart.getData().addAll(combinedSeries, moodValueSeries, activationSeries);
//        moodValueSeries = new XYChart.Series<>();
//
//        moodValueSeries.getData().add(new XYChart.Data<>("Jul", 1));
//        moodValueSeries.getData().add(new XYChart.Data<>("Aug", 5));
    
    }

    @FXML
    public void cbMoodAction() { //Todo: doch besser checkbox?
        log.debug("Checkbox Mood clicked");
        setSeriesVisible(moodValueSeries, cbMood.isSelected());
        log.debug("Mood set visible");
    }

    @FXML
    public void cbActivationAction() {
        log.debug("Checkbox Activation clicked");
        setSeriesVisible(activationSeries, cbCombined.isSelected());
        log.debug("Activation set visible");
    }

    @FXML
    public void cbCombinedAction() {
        log.debug("Checkbox Combined clicked");
        setSeriesVisible(combinedSeries, cbCombined.isSelected());
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

     /*   for (MoodData mood : DataListProvider.moodDataList) {
            String time = mood.getTimeStamp().toString();
            Integer value = mood.getMoodValue();
            moodValueSeries.getData().add(new XYChart.Data<>(time, value));
            log.debug("Mood");
        }*/
    }

    private void makeActivationSeries(){
        activationSeries = new XYChart.Series<>();
        activationSeries.setName("Activation");
        log.debug("Activation made");

/*        for (MoodData mood : DataListProvider.moodDataList) {
            String time = mood.getTimeStamp().toString();
            Integer activation = mood.getActivityLevel();
            activationSeries.getData().add(new XYChart.Data<>(time, activation));
        }*/
    }

    private void makeCombinedSeries(){
        combinedSeries = new XYChart.Series<>();
        combinedSeries.setName("Combined");
/*
        for (MoodData mood : DataListProvider.getWeeklyList()) {
            String time = mood.getTimeStamp().toString();
            Integer combined = (Math.abs(mood.getActivityLevel()) + Math.abs(mood.getMoodValue()))/2; //Todo: Sinnhaft, wie es berechnet wird?
            combinedSeries.getData().add(new XYChart.Data<>(time, combined));
        }*/
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
        
        updateList();
        
        cbMood.setSelected(false);
        cbActivation.setSelected(false);
        cbCombined.setSelected(true);
        cbMoodAction();
        cbActivationAction();
        cbCombinedAction();
    }
    /**
     * called when this controller is deactivated
     */
    @Override
    public void deactivate(){
        super.deactivate();
    }


    public void updateList() {
        Callable<ArrayList> week = new DataListProvider(DataListProvider.StatTimeModus.WEEK, MoodManager.getInstance());
        Callable<ArrayList> day = new DataListProvider(DataListProvider.StatTimeModus.DAY, MoodManager.getInstance());
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<ArrayList> resultWeek = executor.submit(week);
        Future<ArrayList> resultDay = executor.submit(day);
    }
}
