package com.algosenpai.app;


import com.algosenpai.app.controller.MainWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * A GUI for Duke using FXML.
 */
public class GUI extends Application {

    //Initialise the different parts here
    private Parser parser = new Parser();
    private Ui ui = new Ui();
    private Logic logic = new Logic(parser, ui);

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setLogic(logic);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
