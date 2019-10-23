package com.algosenpai.app.ui;


import com.algosenpai.app.logic.Logic;
import com.algosenpai.app.logic.command.Command;

import com.algosenpai.app.ui.components.DialogBox;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class Ui extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;
    @FXML
    private ProgressBar levelProgress;
    @FXML
    private Label playerName;
    @FXML
    private Label playerLevel;
    @FXML
    private ImageView userPic;

    private Logic logic;

    private Image boyImage = new Image(this.getClass().getResourceAsStream("/images/boyplayer.jpg"));
    private Image girlImage = new Image(this.getClass().getResourceAsStream("/images/girlplayer.png"));
    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/unknown.png"));
    private Image senpaiImage = new Image(this.getClass().getResourceAsStream("/images/miku.png"));

    /**
     * Renders the nodes on the GUI.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getChildren().add(DialogBox.getSenpaiDialog(
                "Welcome to AlgoSenpai Adventures! Type 'hello' to start!", senpaiImage));
        userPic.setImage(userImage);
        levelProgress.setProgress(0.0);
    }

    public void setLogic(Logic l) {
        logic = l;
    }

    /**
     * Changes the user Image
     */
    public void changeUserImage(boolean isBoy) {
        if (isBoy) {
            userImage = boyImage;
            userPic.setImage(userImage);
        } else {
            userImage = girlImage;
            userPic.setImage(userImage);
        }

    }
    /**
     * Handles the input of the user and prints the output of the program
     * onto the GUI.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        Command currCommand = logic.parseInputCommand(input);
        String response = logic.executeCommand(currCommand);
        printUserText(input, userImage);
        printSenpaiText(response, senpaiImage);
        exit(input, response);
    }

    /**
     * Creates the dialog box on GUI to show the user what he/she has typed.
     * Clears the user input after processing.
     * @param text the String that user has typed in
     * @param image the profile picture of the user.
     */
    private void printSenpaiText(String text, Image image) {
        dialogContainer.getChildren().add(DialogBox.getSenpaiDialog(text, image));
        userInput.clear();
    }

    /**
     * Creates the dialog box on GUI to show the response of the Senpai.
     * @param text the response of the program.
     * @param image the profile picture of the Senpai.
     */
    private void printUserText(String text, Image image) {
        dialogContainer.getChildren().add(DialogBox.getUserDialog(text, image));
    }

    /**
     * Closes the application.
     * @param input user input.
     */
    private void exit(String input, String response) {
        if (input.equals("exit")) {
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(event -> {
                Platform.exit();
            });
            pause.play();
        }

        //to finish set up
        if (response.equals("You have set your profile! Time to start your journey!")) {
            playerName.setText("Hi, " + logic.getName());
            playerLevel.setText("You are Level " + logic.getLevel());
            changeUserImage(logic.isBoy());
        }
    }
}
