package org.jap.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;

/*
    Created by nika
*/

public class Controller {
    private static final Logger log = LogManager.getLogger(Controller.class);
    
    // Variables
    @FXML
    private AnchorPane rootNode;
    @FXML
    private TextArea consoleOutput;
    @FXML
    private TextField consoleInput;
    
    // Methods
    @FXML
    public void onAction() {
        String textInput = consoleInput.getText();
        consoleInput.setText("");
        log.debug("User Input: " + textInput);
        consoleOutput.appendText("User Input: " + textInput + "\n");
        interpretUserInput(textInput);
    }
    
    private void interpretUserInput(String input) {
        String[] inputArgs = input.split(" ");
        if (
            inputArgs.length>0 && (
                inputArgs[0].toLowerCase(Locale.ROOT).equals("close") ||
                inputArgs[0].toLowerCase(Locale.ROOT).equals("exit") ||
                inputArgs[0].toLowerCase(Locale.ROOT).equals("stop")
            )
        ) {
            log.debug("Exiting: user called exit");
            exitApplication();
        }
    }
    
    private void exitApplication() {
        Stage stage = (Stage) rootNode.getScene().getWindow();
        stage.close();
    }
}
