package org.jap.view;


import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jap.controller.GenericController;
import org.jap.controller.MoodStatsViewController;
import org.jap.model.datahandler.DataListProvider;
import org.jap.model.mood.MoodData;
import org.jap.model.mood.MoodManager;

import java.util.ArrayList;

/*
    Created by nika
*/

public class MoodStatsView {
    private static final Logger log = LogManager.getLogger(MoodStatsView.class);




    /**
     * Controller for the Stats View
     * There are three types of view on the linegraph: only mood-level, only activation level and the combined view where the two values are
     * calculated together into a single graph
     *
     * Created by Jannika
     */

    public class MoodStatsViewController extends GenericController {
        private static final Logger log = LogManager.getLogger(org.jap.controller.MoodStatsViewController.class);

        @FXML
        private CheckBox cbMood;
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


        private org.jap.controller.MoodStatsViewController.StatsStates statState;
        private org.jap.controller.MoodStatsViewController.TimeOption timeOption;
        private final static org.jap.controller.MoodStatsViewController.StatsStates DEFAULT_STATS_STATE = org.jap.controller.MoodStatsViewController.StatsStates.CB_COMBINED;

        private ArrayList<MoodData> timedList;

        //Todo: maybe not private? -> MoodManager könnte drauf zugreifen?

        private XYChart.Series<String, Integer> moodValueSeries;
        private XYChart.Series<String, Integer> activationSeries;
        private XYChart.Series<String, Integer> combinedSeries;

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

            lineChart.getData().addAll(combinedSeries, moodValueSeries,activationSeries);
            moodValueSeries = new XYChart.Series<>();

            moodValueSeries.getData().add(new XYChart.Data<>("Jul", 1));
            moodValueSeries.getData().add(new XYChart.Data<>("Aug", 5));
            updateChart();
        }

        @FXML
        public void cbMoodAction() { //Todo: doch besser checkbox?
            log.debug("Checkbox Mood clicked");
            statState = org.jap.controller.MoodStatsViewController.StatsStates.CB_MOOD;
            lineChart.getData().add(moodValueSeries);
        }

        @FXML
        public void cbActivationAction() {
            log.debug("Checkbox Activation clicked");
            statState = org.jap.controller.MoodStatsViewController.StatsStates.CB_ACTIVATION;
            lineChart.getData().add(activationSeries);
            log.debug("Data Added");
        }

        @FXML
        public void cbCombinedAction() {
            log.debug("Checkbox Combined clicked");
            statState = org.jap.controller.MoodStatsViewController.StatsStates.CB_COMBINED;
            lineChart.getData().add(combinedSeries);
            log.debug("Data for Combined Added");
            setSeriesVisible(combinedSeries, true);
            log.debug("Combined set visible");
        }

        //Display Data Methods

        private void updateChart(){
            //Liste aus Linien (Series), die dargestellt werden

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
    }
}
