package com.example.project;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import static com.example.project.loginController.temail;

public class completeController implements Initializable {

    @FXML
    private Button HomeB;

    @FXML
    private Button cSchedule;

    @FXML
    private ImageView a;
    @FXML
    private ImageView b;

    @FXML
    private TableView<completedView> completed;
    @FXML
    private TableColumn<completedView, String> gid;
    @FXML
    private TableColumn<completedView, String> fydp;
    @FXML
    private TableColumn<completedView, String> day;
    @FXML
    private TableColumn<completedView, String> complete;
    ObservableList<completedView> list = FXCollections.observableArrayList();


    @FXML
    void createScBT(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CreateSchedule.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void homeBT(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("TeacherHome.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TranslateTransition translateA = new TranslateTransition(Duration.seconds(3), a);
        translateA.setFromX(-30);
        translateA.setToX(30);
        translateA.setCycleCount(TranslateTransition.INDEFINITE);
        translateA.setAutoReverse(true);
        TranslateTransition translateB = new TranslateTransition(Duration.seconds(3), b);
        translateB.setFromX(30);
        translateB.setToX(-30);
        translateB.setCycleCount(TranslateTransition.INDEFINITE);
        translateB.setAutoReverse(true);
        translateA.play();
        translateB.play();
        completed.setRowFactory(tv -> {
            return new TableRow<completedView>() {
                @Override
                protected void updateItem(completedView item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setStyle("");
                    } else {
                        setMinHeight(50);
                        setPrefHeight(50);
                        setMaxHeight(50);
                    }
                }
            };
        });


        String driver = "com.mysql.cj.jdbc.Driver";
        String dbUrl = "jdbc:mysql://localhost:3306/uiubuddy";
        String userName = "root";
        String password = "";

        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(dbUrl, userName, password);
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM `counseling` WHERE memail='" + temail + "' AND complete='Completed'");

            while (rs.next()) {
                list.add(new completedView(rs.getString("gid"), rs.getString("fydp"), rs.getString("day"), rs.getString("complete")));
            }
            conn.close();
            rs.close();
        } catch (Exception e) {

        }
        gid.setCellValueFactory(new PropertyValueFactory<completedView, String>("gid"));
        fydp.setCellValueFactory(new PropertyValueFactory<completedView, String>("fydp"));
        day.setCellValueFactory(new PropertyValueFactory<completedView, String>("day"));
        complete.setCellValueFactory(new PropertyValueFactory<completedView, String>("complete"));


      completed.setItems(list);
    }


    @FXML
    void runningBT(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("runningG.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void completeBT(ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("completeG.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
