package com.example.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {



    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    private void handleRegisterLabelClicked(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("index.fxml"));
            Parent registerPage = loader.load();
            Scene registerScene = new Scene(registerPage);

            Stage primaryStage = (Stage) ((Label) event.getSource()).getScene().getWindow();
            primaryStage.setScene(registerScene);



            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}