package com.algosenpai.app;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class DialogBox extends HBox {

    private Label text;
    private ImageView displayPicture;

    public DialogBox(Label l) {
        text = l;


        text.setWrapText(true);

        this.setAlignment(Pos.TOP_RIGHT);
        this.getChildren().addAll(text);
    }
}
