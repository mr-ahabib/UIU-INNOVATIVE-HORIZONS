package com.example.project;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class web extends Application {

    // launch the application
    public void start(Stage stage)
    {
        try {

            // set title for the stage
            stage.setTitle("creating Webview");

            // create a webview object
            WebView w = new WebView();

            // get the web engine
            WebEngine e = w.getEngine();

            // load a website
            e.load("https://www.google.com");

            // create a scene
            Scene scene = new Scene(w, w.getPrefWidth(),
                    w.getPrefHeight());

            // set the scene
            stage.setScene(scene);

            stage.show();
        }

        catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }

    // Main Method
    public static void main(String args[])
    {

        // launch the application
        launch(args);
    }
}
