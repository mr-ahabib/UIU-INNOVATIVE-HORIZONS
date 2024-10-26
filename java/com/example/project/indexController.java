package com.example.project;

import javafx.fxml.FXML;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class indexController {
    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label uiuLabel, innovativeLabel, horizonsLabel;
    private Scene scene;
    private Parent root;
    private Stage stage;
    @FXML
    private Button studentLogin;

    @FXML
    private Button teacherLogin;

    @FXML
    private Button admin;

    @FXML
    void studentLoginBt(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("Stlogin.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);

        stage.show();

    }

    @FXML
    void teacherLoginBt(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void adminB(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("adminLogin.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void initialize() {

        TranslateTransition uiuTransition = new TranslateTransition(Duration.seconds(7), uiuLabel);
        uiuTransition.setToY(100);
        uiuTransition.setFromY(0);
        uiuTransition.setCycleCount(TranslateTransition.INDEFINITE);
        uiuTransition.setAutoReverse(true);

        TranslateTransition innovativeTransition = new TranslateTransition(Duration.seconds(7), innovativeLabel);
        innovativeTransition.setToY(100);
        innovativeTransition.setFromY(0);
        innovativeTransition.setCycleCount(TranslateTransition.INDEFINITE);
        innovativeTransition.setAutoReverse(true);

        TranslateTransition horizonsTransition = new TranslateTransition(Duration.seconds(7), horizonsLabel);
        horizonsTransition.setToY(100);
        horizonsTransition.setFromY(0);
        horizonsTransition.setCycleCount(TranslateTransition.INDEFINITE);
        horizonsTransition.setAutoReverse(true);


        uiuTransition.play();
        innovativeTransition.play();
        horizonsTransition.play();
    }


}
