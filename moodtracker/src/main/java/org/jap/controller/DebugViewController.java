package org.jap.controller;

import javafx.application.Platform;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
    Created by Peter
*/

/**
 * Controller for the DebugView
 */
public class DebugViewController extends GenericController {
    private static final Logger log = LogManager.getLogger(DebugViewController.class);
    
    // Variables
    private static final int DEFAULT_AMOUNT = 50;
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
    private final Runnable generatingRunnable = this::generate;
    
    // Methods
    @FXML public void txfAmountAction() {
        log.debug("AmountAction Input: "+ txfAmount.getText());
        txaSomeText.appendText("You think that's enough?\n");
        try {
            amount = Integer.parseInt(txfAmount.getText());
            if (amount>1000) {
                amount = 1000;
                txfAmount.setText(amount+"");
                txaSomeText.appendText("Dont't take it too far! I won't calculate more than a thousand at once!\n");
            }
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
            txaSomeText.appendText("Well, that could take a while now...\n");
            
            getSceneManager().setDisable(true);
            priLoadingSnail.setVisible(true);
            
            new Thread(generatingRunnable).start();
        }
    }
    
    @FXML public void mniMagicAction() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to format your system?");
        Optional<ButtonType> result = alert.showAndWait()
                .filter(response -> response == ButtonType.OK);
        if (result.isPresent()) {
            MoodManager.getInstance().deleteAllMoods();
            txaSomeText.appendText("All moods magically disappeared from the Database ^w^\n");
            log.debug("User requested magical vanishing of all the moods.");
        } else {
            txaSomeText.appendText("You don't wanna see my magic tricks?\n");
            log.debug("Note to myself: User isn't interested in magic tricks!");
        }
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
        tbtToggleMe.setSelected(true); // fancy mode is now default
        
        txaSomeText.appendText("Welcome! :)\n");
    }
    
    @Override
    public void activate() {
        super.activate();
    }
    
    private synchronized void generate() {
        final int finalAmount = amount;
        if (finalAmount>0) {
            List<MoodData> moods = new ArrayList<>();
            
            for (int i = 0; i<finalAmount; i++) {
                LocalDateTime timestamp = LocalDateTime.now();
                switch (timeUnit) {
                    case MINUTES -> timestamp = timestamp.plusMinutes(i);
                    case HOURS -> timestamp = timestamp.plusHours(i);
                    case DAYS -> timestamp = timestamp.plusDays(i);
                    case WEEKS -> timestamp = timestamp.plusWeeks(i);
                }
                moods.add(new MoodData("Generated Mood Nr."+(i+1), "Mood Generated using Debug View", timestamp, activity, moodValue, false));
                nextGeneration();
                if (tbtToggleMe.isSelected()) { // artificial delay if togglebtn is on
                    long delay = 5000 / finalAmount;
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                final int finalI = i;
                Platform.runLater(() -> prbFillMeUp.setProgress((double) (finalI+1)/finalAmount));
            }
            
            // Finished Generating Moods
            Platform.runLater(() -> {
                MoodManager.getInstance().addAllMoods(moods);
                prbFillMeUp.setProgress(100);
            });
        }
        
        Platform.runLater(() -> priLoadingSnail.setVisible(false));
        if (finalAmount>1)
            Platform.runLater(() -> txaSomeText.appendText("Congrats. There are now "+finalAmount+" more moods on your hard drive!\n"));
        else if (finalAmount==1)
            Platform.runLater(() -> txaSomeText.appendText("Sure...\nTake it easy...\nOne by one...\nBreath in...\nBreath out...\nNow there are 6 more line in this text area...\n"));
        else if (finalAmount==0)
            Platform.runLater(() -> txaSomeText.appendText("I give you 0 sweets. Are you happy now?\n"));
        else
            Platform.runLater(() -> txaSomeText.appendText("This text should not appear...\nYou broke my software! >:(\n"));
        if (tbtToggleMe.isSelected()) {
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                log.error(e.fillInStackTrace());
            }
        }
        Platform.runLater(() -> {
            prbFillMeUp.setProgress(0);
            getSceneManager().setDisable(false);
        });
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
