package com.example.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;

public class webViewController {

    @FXML
    private WebView tweb;
    @FXML
    private Button backB;

    WebView w = new WebView();

    public void loadURL(String url) {
        WebEngine webEngine = tweb.getEngine();
        webEngine.load(url);
    }



    @FXML
    void backBT(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Book.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
