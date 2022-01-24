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
    private boolean toldYou = false;
    
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
    
    // threads
    private final Thread generatingThread = new Thread() {
        @Override
        public void run() {
            if (amount>0) {
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
                    if (tbtToggleMe.isSelected()) { // artificial delay if togglebtn is on
                        long delay = 10000 / amount;
                        try {
                            Thread.sleep(delay);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    prbFillMeUp.setProgress((double)i/amount);
                }
                prbFillMeUp.setProgress(100);
            }

//                    cmiDisabled.setSelected(true); // prevents accidental double click
            priLoadingSnail.setVisible(false);
            if (amount>1)
                txaSomeText.appendText("Congrats. There are now "+amount+" more moods on your hard drive!\n");
            else if (amount==1)
                txaSomeText.appendText("Sure...\nTake it easy...\nOne by one...\nBreath in...\nBreath out...\nNow there are 6 more line in this text area...\n");
            else if (amount==0)
                txaSomeText.appendText("I give you 0 sweets. Are you happy now?\n");
            else
                txaSomeText.appendText("This text should not appear...\nYou broke my software! >:(\n");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            prbFillMeUp.setProgress(0);
            btnGenerate.setDisable(false);
            txfAmount.setDisable(false);
        }
    };
    
    // Methods
    @FXML public void txfAmountAction() {
        log.debug("AmountAction Input: "+ txfAmount.getText());
        txaSomeText.appendText("You think that's enough?\n");
        try {
            amount = Integer.parseInt(txfAmount.getText());
            txfAmount.setStyle("");
        } catch (NumberFormatException e) {
            log.debug("Amount Spinner: only integer values allowed");
            txaSomeText.appendText("Don't worry. Mistakes happen... :)\n");
            txfAmount.setStyle("-fx-text-fill: red");
        }
    }
    
    @FXML public void btnGenerateAction() {
        if (cmiDisabled.isSelected()) {
            txaSomeText.appendText("Nothing happens...\nWell, what did you expect to happen when you deactivate the Button?\n");
            log.debug("Button deactivated. Right click -> uncheck disabled, to activate");
            toldYou = true;
        }
        else {
            txaSomeText.appendText("Well, that can take a while now...\n");
    
            btnGenerate.setDisable(true);
            priLoadingSnail.setVisible(true);
            txfAmount.setDisable(true);
    
/*            if (amount>0) {
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
                    if (tbtToggleMe.isSelected()) { // artificial delay if togglebtn is on
                        long delay = 10000 / amount;
                        try {
                            Thread.sleep(delay);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    prbFillMeUp.setProgress((double)i/amount);
                }
                prbFillMeUp.setProgress(100);
            }

//                    cmiDisabled.setSelected(true); // prevents accidental double click
            priLoadingSnail.setVisible(false);
            if (amount>1)
                txaSomeText.appendText("Congrats. There are now "+amount+" more moods on your hard drive!\n");
            else if (amount==1)
                txaSomeText.appendText("Sure...\nTake it easy...\nOne by one...\nBreath in...\nBreath out...\nNow there are 6 more line in this text area...\n");
            else if (amount==0)
                txaSomeText.appendText("I give you 0 sweets. Are you happy now?\n");
            else
                txaSomeText.appendText("This text should not appear...\nYou broke my software! >:(\n");
    
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            prbFillMeUp.setProgress(0);
            btnGenerate.setDisable(false);
            txfAmount.setDisable(false);*/
            
            try {
                generatingThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            generatingThread.start();
        }
    }
    
    @FXML public void mniMagicAction() {
        MoodManager.getInstance().getMoods().forEach(MoodManager.getInstance()::deleteMood);
        log.debug("All moods magically disappeared from the Database :(");
    }
    
    @FXML public void cmiDisabledAction() {
        if (cmiDisabled.isSelected())
            txaSomeText.appendText("I'm quite sure you needed that thing...\nBut ok, you're the boss!\n");
        else if (toldYou)
            txaSomeText.appendText("told ya\n");
        else
            txaSomeText.appendText("Sure, can do.\n");
    }
    
    @FXML public void tbtToggleMeAction() {
        if (tbtToggleMe.isSelected()) {
            tbtToggleMe.setText(":)");
            txaSomeText.appendText(":)\n");
        }
        else {
            tbtToggleMe.setText(":(");
            txaSomeText.appendText(":(\n");
        }
    }
    
    @FXML public void clpCoolorAction() {
        txaSomeText.setStyle(
            "-fx-control-inner-background: "+"#"+clpCoolor.getValue().toString().substring(2)+";"+
            "-fx-background-color: "+"#"+clpCoolor.getValue().invert().toString().substring(2)+";"+
            "-fx-text-fill: "+"#"+clpCoolor.getValue().invert().toString().substring(2)+";"
        );
        log.debug("Color: "+clpCoolor.getValue());
        txaSomeText.appendText("Go ahead. Try some more colors!\n");
    }
    
    @FXML public void chbAAction() {
        if (chbA.isSelected())
            txaSomeText.appendText("A Choice!\n");
    }
    
    @FXML public void chbBAction() {
        if (chbB.isSelected())
            txaSomeText.appendText("Believable Choice!\n");
    }
    
    @FXML public void chbCAction() {
        if (chbC.isSelected())
            txaSomeText.appendText("Countable Choice!\n");
    }
    
    @FXML public void chbDAction() {
        if (chbD.isSelected())
            txaSomeText.appendText("Descend Choice!\n");
    }
    
    @FXML public void rdbMinutesAction() {
        timeUnit = Selection.MINUTES;
        txaSomeText.appendText("Take it easy! You have highly frequent mood swings if you feel different every minute.\n");
    }
    
    @FXML public void rdbHoursAction() {
        timeUnit = Selection.HOURS;
        txaSomeText.appendText("Don't you have better things to do then to add a mood every hour?\n");
    }
    
    @FXML public void rdbDaysAction() {
        timeUnit = Selection.DAYS;
        if (amount == 365)
            txaSomeText.appendText("You're pretty disciplined! (yes, i really wrote an if statement for that joke...)\n");
        else
            txaSomeText.appendText("If you can keep that up for 365 Days, you're pretty disciplined.\n");
    }
    
    @FXML public void rdbWeeksAction() {
        timeUnit = Selection.WEEKS;
        double years = (double) amount/52;
        txaSomeText.appendText("How long do you want that diagram to be? "+(int)(years*10)/10+"."+(int)(years*10)%10+" years?\n");
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
        txaSomeText.appendText("Welcome! :)\n");
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
