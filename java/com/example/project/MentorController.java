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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MentorController implements Initializable {
    public static String meid;
    public static String fy;

    @FXML
    private Button GroupB;

    @FXML
    private Button HomeB;


    @FXML
    private Button gob;


    @FXML
    private ImageView a;
    @FXML
    private ImageView b;


    @FXML
    private TableView<mentorsTable> mentors;

    @FXML
    private TableColumn<mentorsTable, String> name;
    @FXML
    private TableColumn<mentorsTable, String> email;
    @FXML
    private TableColumn<mentorsTable, String> fydp;
    ObservableList<mentorsTable> list2 = FXCollections.observableArrayList();




    @FXML
    private Button createGroupBT;
    @FXML
    private Button MviewB;

    @FXML
    void MviewBT(ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Book.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void CgroupBt(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Cgroup.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void GroupBT(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Groups.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void homeBT(ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("profile.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
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

        mentors.setRowFactory(tv -> {
            return new TableRow<mentorsTable>() {
                @Override
                protected void updateItem(mentorsTable item, boolean empty) {
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
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM `schedule`");
            while (rs.next()) {
                list2.add(new mentorsTable(rs.getString("name"), rs.getString("email"), rs.getString("fydp")));
            }
            conn.close();
            rs.close();
        } catch (Exception e) {

        }
        name.setCellValueFactory(new PropertyValueFactory<mentorsTable, String>("name"));
        email.setCellValueFactory(new PropertyValueFactory<mentorsTable, String>("email"));
        fydp.setCellValueFactory(new PropertyValueFactory<mentorsTable, String>("fydp"));

        mentors.setItems(list2);
        mentors.setOnMouseClicked(e -> {


                    events();
                }

        );
    }
    private void events() {
        for (mentorsTable pick : mentors.getSelectionModel().getSelectedItems()) {
//            for (int i = 1; i <= 1; i++)
            meid = pick.getEmail();
            fy=pick.getFydp();


        }
    }


    @FXML
    void goBT(ActionEvent event) throws IOException , SQLException, NoSuchAlgorithmException,ClassNotFoundException {

        String driver = "com.mysql.cj.jdbc.Driver";
        String databaseurl = "jdbc:mysql://localhost:3306/uiubuddy";
        String username = "root";
        String passwords = "";
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(databaseurl, username, passwords);
        Parent root = FXMLLoader.load(getClass().getResource("Book.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        Window owner = gob.getScene().getWindow();
        showAlert(Alert.AlertType.CONFIRMATION, owner, "ID", "Mentor ID Picked");

    }
    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    @FXML
    void mProfileBT(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Profile1.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void reqBT(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("requests.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void requBT(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("GroupRequest.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
