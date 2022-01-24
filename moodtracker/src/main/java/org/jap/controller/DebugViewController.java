package org.jap.controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jap.model.mood.MoodData;
import org.jap.model.mood.MoodManager;
import org.jap.view.SceneManager;

import java.time.LocalDateTime;

/*
    Created by Peter
*/

/**
 *
 */
public class DebugViewController extends GenericController {
    private static final Logger log = LogManager.getLogger(DebugViewController.class);
    
    // Variables
    
    private static final int DEFAULT_AMOUNT = 100;
    private int amount = DEFAULT_AMOUNT;
    
    private int activity = MoodData.DEFAULT_ACTIVITYLEVEL;
    private int moodValue = MoodData.DEFAULT_MOODVALUE;
    
    private Selection timeUnit = Selection.MINUTES;
    
    // FXML Fields
    @FXML private AnchorPane root;
    @FXML private TextField txfAmount;
    @FXML private Button btnGenerate;
    @FXML private MenuItem mniMagic;
    @FXML private CheckMenuItem cmiDisabled;
    @FXML private ToggleButton tbtToggleMe;
    @FXML private ProgressIndicator priLoadingSnail;
    @FXML private ProgressBar prbFillMeUp;
    @FXML private ColorPicker clpCoolor;
    @FXML private TextArea txaSomeText;
    @FXML private CheckBox chbA;
    @FXML private CheckBox chbB;
    @FXML private CheckBox chbC;
    @FXML private CheckBox chbD;
    @FXML private RadioButton rdbMinutes;
    @FXML private RadioButton rdbHours;
    @FXML private RadioButton rdbDays;
    @FXML private RadioButton rdbWeeks;
    
    // Inner Classes
    private enum Selection {
        MINUTES,
        HOURS,
        DAYS,
        WEEKS
    }
    
    // Methods
    @FXML public void txfAmountAction() {
        log.debug("AmountAction Input: "+ txfAmount.getText());
        try {
            amount = Integer.parseInt(txfAmount.getText());
            txfAmount.setStyle("");
        } catch (NumberFormatException e) {
            log.debug("Amount Spinner: only integer values allowed");
            txfAmount.setStyle("-fx-text-fill: red");
        }
    }
    
    @FXML public void btnGenerateAction() {
        if (cmiDisabled.isSelected()) {
            log.debug("Button deactivated. Right click -> uncheck disabled, to activate");
        }
        else {
            
            Thread generatingThread = new Thread() {
                @Override
                public void run() {
                    super.run();
    
                    btnGenerate.setDisable(true);
                    priLoadingSnail.setVisible(true);
    
                    for (int i = 0; i<amount; i++) {
                        LocalDateTime timestamp = LocalDateTime.now();
                        switch (timeUnit) {
                            case MINUTES -> timestamp = timestamp.plusMinutes(i);
                            case HOURS -> timestamp = timestamp.plusHours(i);
                            case DAYS -> timestamp = timestamp.plusDays(i);
                            case WEEKS -> timestamp = timestamp.plusWeeks(i);
                        }
                        MoodManager.getInstance().createMood("Generated Mood Nr."+(i+1), "Mood Generated using Debug View", timestamp, activity, moodValue);
                        nextGeneration();
                        long delay = 10000/amount;
//                        try {
//                            Thread.sleep(delay); // artificial delay
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
                        prbFillMeUp.setProgress((double)i/amount);
                    }
    
//                    cmiDisabled.setSelected(true); // prevents accidental double click
                    priLoadingSnail.setVisible(false);
    
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    prbFillMeUp.setProgress(0);
                    btnGenerate.setDisable(false);
                }
            };
            
            generatingThread.start();
        }
    }
    
    @FXML public void mniMagicAction() {
        MoodManager.getInstance().getMoods().forEach(MoodManager.getInstance()::deleteMood);
        log.debug("Magically all Mood disappeared from the Database :(");
    }
    
    @FXML public void cmiDisabledAction() {
    
    }
    
    @FXML public void tbtToggleMeAction() {
    
    }
    
    @FXML public void clpCoolorAction() {
    
    }
    
    @FXML public void chbAAction() {
    
    }
    
    @FXML public void chbBAction() {
    
    }
    
    @FXML public void chbCAction() {
    
    }
    
    @FXML public void chbDAction() {
    
    }
    
    @FXML public void rdbMinutesAction() {
        timeUnit = Selection.MINUTES;
    }
    
    @FXML public void rdbHoursAction() {
        timeUnit = Selection.HOURS;
    }
    
    @FXML public void rdbDaysAction() {
        timeUnit = Selection.DAYS;
    }
    
    @FXML public void rdbWeeksAction() {
        timeUnit = Selection.WEEKS;
    }
    
    @Override
    public void initController(SceneManager sceneManager, Parent scene) {
        super.initController(sceneManager, scene);
        txfAmount.setText(amount+"");
        switch (timeUnit) {
            case MINUTES -> rdbMinutes.setSelected(true);
            case HOURS -> rdbHours.setSelected(true);
            case DAYS -> rdbDays.setSelected(true);
            case WEEKS -> rdbWeeks.setSelected(true);
        }
    }
    
    @Override
    public void activate() {
        super.activate();
    }
    
    private void nextGeneration() {
        activity = next(activity,MoodData.MIN_ACTIVITYLEVEL,MoodData.MAX_ACTIVITYLEVEL);
        moodValue = next(moodValue,MoodData.MIN_MOODVALUE,MoodData.MAX_MOODVALUE);
    }
    
    private int next(int current, int min, int max) {
        int rd = (int) Math.floor(Math.random()*2);
        current += (rd - (1 - rd));
        return Math.min(Math.max(min, current), max);
    }
}
