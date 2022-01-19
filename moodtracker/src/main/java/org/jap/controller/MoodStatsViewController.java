package org.jap.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.*;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jap.model.mood.MoodData;
import org.jap.model.mood.MoodManager;
import org.jap.util.extfx.scene.chart.DateAxis;
import org.jap.view.SceneManager;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Date;
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
    @FXML private LineChart<Date, Number> lineChart; //Todo: private observable List?
    @FXML private CategoryAxis xAxis;
    @FXML private NumberAxis yAxis;

    private StatsStates statState;
    private final static StatsStates DEFAULT_STATS_STATE = StatsStates.RBTN_COMBINED;

    private List<MoodData> moodDataList; //Todo: maybe not private? -> MoodManager könnte drauf zugreifen?

    private XYChart.Series<Date, Number> moodValueSeries = new XYChart.Series<>();
    private XYChart.Series<Date, Number> activationSeries = new XYChart.Series<>();
    private XYChart.Series<Date, Number> combinedSeries = new XYChart.Series<>();
    
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
    public void initController(SceneManager sceneManager, Parent scene) { //Todo: init schön machen
        super.initController(sceneManager, scene);                          //Todo: sinnvolle Reihenfolge machen
        moodDataList = MoodManager.getInstance().getMoods(); //Kopie der Moodliste
        
        statState = DEFAULT_STATS_STATE;
        
        ArrayList<XYChart.Series<Date,Number>> list = new ArrayList<>();
        list.add(0,moodValueSeries);
        list.add(1,activationSeries);
        list.add(2,combinedSeries);
        
        LineChart<Date, Number> testChart = new LineChart<Date,Number>(new DateAxis(), yAxis, FXCollections.observableArrayList(list));
        
        testChart.setAnimated(false);
        testChart.setStyle(lineChart.getStyle());
        testChart.getStylesheets().addAll(lineChart.getStylesheets());
        testChart.getXAxis().setTickMarkVisible(true);
        yAxis.setAutoRanging(false);
        yAxis.setUpperBound(10);
        yAxis.setLowerBound(0);
        
        lineChart.setVisible(false);
        ((Pane) lineChart.getParent()).getChildren().set(1,testChart);
        
        moodValueSeries.setName("Moods");
        activationSeries.setName("Activation");
        combinedSeries.setName("Combined");
        
//        lineChart.getData().addAll(combinedSeries, moodValueSeries,activationSeries);
//        moodValueSeries = new XYChart.Series<>();
//        moodValueSeries.getData().add(new XYChart.Data<>("Jul", 1));
//        moodValueSeries.getData().add(new XYChart.Data<>("Aug", 5));
//        updateChart();
    }
    
    @FXML
    public void rBtnMoodAction() { //Todo: doch besser checkbox?
        log.debug("Radiobutton Mood clicked");
        statState = StatsStates.RBTN_MOOD;
        setSeriesVisible(moodValueSeries,true);
        setSeriesVisible(activationSeries,false);
        setSeriesVisible(combinedSeries,false);
    }
    
    @FXML
    public void rBtnActivationAction() {
        log.debug("Radiobutton Activation clicked");
        statState = StatsStates.RBTN_ACTIVATION;
        setSeriesVisible(moodValueSeries,false);
        setSeriesVisible(activationSeries,true);
        setSeriesVisible(combinedSeries,false);
    }
    
    @FXML
    public void rBtnCombinedAction() {
        log.debug("Radiobutton Combined clicked");
        statState = StatsStates.RBTN_COMBINED;
        setSeriesVisible(moodValueSeries,true);
        setSeriesVisible(activationSeries,true);
        setSeriesVisible(combinedSeries,true);
    }
    
    //Display Data Methods
    
    private void updateChart() {
        moodDataList = MoodManager.getInstance().getMoods(); // Aktualisiert moodDataList
        
        moodValueSeries.getData().clear(); // löscht die linien
        activationSeries.getData().clear();
        combinedSeries.getData().clear();
        
        for (MoodData m : moodDataList) // fügt den linien punkte hinzu
        {
//            String time = m.getTimeStamp().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
            Date time = Date.from(m.getTimeStamp().atZone(ZoneId.systemDefault()).toInstant());
            int value = m.getMoodValue();
            int activation = m.getActivityLevel();
            int combined = (Math.abs(activation) + Math.abs(value))/2; //Todo: Sinnhaft, wie es berechnet wird?
            
            moodValueSeries.getData().add(new XYChart.Data<>(time, value));
            activationSeries.getData().add(new XYChart.Data<>(time, activation));
            combinedSeries.getData().add(new XYChart.Data<>(time, combined));
        }

//        setSeriesColor(moodValueSeries, Color.rgb(255,128,0)); // farben hinzufügen
//        setSeriesColor(activationSeries, Color.rgb(0,128,255));
//        setSeriesColor(combinedSeries, Color.rgb(255,128,255));
    }
    
    private void setSeriesVisible(XYChart.Series<?,?> s, boolean visible) {
        s.getNode().setVisible(visible); // Toggle visibility of line
        for (XYChart.Data<?,?> d : s.getData()) {
            if (d.getNode() != null) {
                d.getNode().setVisible(visible); // Toggle visibility of every node in the series
            }
        }
    }
    
//    private void setSeriesColor(XYChart.Series<?,?> s, Color color) {
//        Node line = s.getNode().lookup(".chart-series-line");
//        String rgb = String.format("%d, %d, %d",
//                (int) (color.getRed() * 255),
//                (int) (color.getGreen() * 255),
//                (int) (color.getBlue() * 255));
//        line.setStyle("-fx-stroke: rgba(" + rgb + ", 1.0);");
//    }
    
    private void makeMoodValueSeries() {
        moodValueSeries = new XYChart.Series<>();
        moodValueSeries.setName("Moods");

        for (MoodData mood : moodDataList) {
            String time = mood.getTimeStamp().toString();
            Integer value = mood.getMoodValue();
//            moodValueSeries.getData().add(new XYChart.Data<>(time, value));
        }
        log.debug("MoodValueSeries made");
    }
    
    private void makeActivationSeries() {
        activationSeries = new XYChart.Series<>();
        activationSeries.setName("Activation");

        for (MoodData mood : moodDataList) {
            String time = mood.getTimeStamp().toString();
            Integer activation = mood.getActivityLevel();
//            activationSeries.getData().add(new XYChart.Data<>(time, activation));
        }
        log.debug("Activation made");
    }
    
    private void makeCombinedSeries() {
        combinedSeries = new XYChart.Series<>();
        combinedSeries.setName("Combined");

        for (MoodData mood : moodDataList) {
            String time = mood.getTimeStamp().toString();
            Integer combined = (Math.abs(mood.getActivityLevel()) + Math.abs(mood.getMoodValue()))/2; //Todo: Sinnhaft, wie es berechnet wird?
//            combinedSeries.getData().add(new XYChart.Data<>(time, combined));
        }
    }
    
    /**
     * called when this controller is activated
     */
    @Override
    public void activate() {
        super.activate();
        updateChart();
        
        switch(statState){
            case RBTN_MOOD -> {
                rBtnMood.setSelected(true);
                rBtnMoodAction();
            }
            case RBTN_ACTIVATION -> {
                rBtnActivation.setSelected(true);
                rBtnActivationAction();
            }
            case RBTN_COMBINED -> {
                rBtnCombined.setSelected(true);
                rBtnCombinedAction();
            }
        }
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
