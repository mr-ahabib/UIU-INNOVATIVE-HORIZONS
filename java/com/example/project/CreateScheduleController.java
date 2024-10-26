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
import java.sql.*;
import java.util.ResourceBundle;

import static com.example.project.loginController.temail;
import static com.example.project.loginController.tname;


public class CreateScheduleController implements Initializable {
    public static String del;
    @FXML
    private Button addB;
    @FXML
    private TextField link;

    @FXML
    private TableView<createdView> createdFydp;
    @FXML
    private TableColumn<createdView, String> email;
    @FXML
    private TableColumn<createdView, String> day;
    @FXML
    private TableColumn<createdView, String> fydp;

    ObservableList<createdView> list = FXCollections.observableArrayList();
    @FXML
    private ImageView a;
    @FXML
    private ImageView b;



    @FXML
    private TextField End;
    @FXML
    private TextField Start;
    @FXML
    private Button sbt;
    @FXML
    private TextField sday;
    @FXML
    private TextField tfydp;
    @FXML
    private Button HomeB;
    @FXML
    private Button sdlt;


    @FXML
    private TextField id;

    @FXML
    void homeBT(ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("TeacherHome.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    @FXML
    void submitBT(ActionEvent event) throws IOException, NoSuchAlgorithmException,ClassNotFoundException, SQLException {
        Window owner = sbt.getScene().getWindow();
        String driver="com.mysql.cj.jdbc.Driver";
        String dbUrl="jdbc:mysql://localhost:3306/uiubuddy";
        String userName="root";
        String password="";
        Class.forName(driver);
        Connection conn= DriverManager.getConnection(dbUrl,userName,password);
        String topic=tfydp.getText();
        String da=sday.getText();
        String star=Start.getText();
        String en=End.getText();
        String sql="INSERT INTO `schedule`(`name`, `email`, `fydp`, `day`, `start`, `end`) VALUES (?,?,?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1,tname);
        ps.setString(2,temail);
        ps.setString(3,topic);
        ps.setString(4,da);
        ps.setString(5,star);
        ps.setString(6,en);
        ps.executeUpdate();
        conn.close();
        showAlert(Alert.AlertType.CONFIRMATION, owner, "Schedule Create",
                "Schedule created Successfully");
        tfydp.setText("");
        sday.setText("");
        Start.setText("");
        End.setText("");

    }
    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    public void initialize(URL url, ResourceBundle rb){


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

        createdFydp.setRowFactory(tv -> {
            return new TableRow<createdView>() {
                @Override
                protected void updateItem(createdView item, boolean empty) {
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
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM `schedule` WHERE email='" + temail + "'");

            while (rs.next()) {
                list.add(new createdView(rs.getString("email"),rs.getString("day"), rs.getString("fydp")));
            }
            conn.close();
            rs.close();
        } catch (Exception e) {

        }
        email.setCellValueFactory(new PropertyValueFactory<createdView,String>("email"));
        day.setCellValueFactory(new PropertyValueFactory<createdView, String>("day"));
        fydp.setCellValueFactory(new PropertyValueFactory<createdView, String>("fydp"));

        createdFydp.setItems(list);
        createdFydp.setOnMouseClicked(e -> {


                    events();
                }

        );

    }
    private void events() {
        for (createdView pick : createdFydp.getSelectionModel().getSelectedItems()) {
//            for (int i = 1; i <= 1; i++)
            del = pick.getFydp();


        }
    }
    @FXML
    void sdltBT(ActionEvent event) throws IOException,NoSuchAlgorithmException,SQLException,ClassNotFoundException {
        String driver = "com.mysql.cj.jdbc.Driver";
        String databaseurl = "jdbc:mysql://localhost:3306/uiubuddy";
        String username = "root";
        String passwords = "";
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(databaseurl, username, passwords);
        String sql1 = "DELETE FROM `schedule` WHERE fydp='" + del + "'";

        PreparedStatement ps = conn.prepareStatement(sql1);
        ps.executeUpdate();
        conn.close();
        Parent root = FXMLLoader.load(getClass().getResource("CreateSchedule.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void addBT(ActionEvent event) throws IOException,NoSuchAlgorithmException,SQLException,ClassNotFoundException {
        Window owner = addB.getScene().getWindow();
        String driver = "com.mysql.cj.jdbc.Driver";
        String databaseurl = "jdbc:mysql://localhost:3306/uiubuddy";
        String username = "root";
        String passwords = "";
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(databaseurl, username, passwords);
        String tlink= link.getText();
        String sql="INSERT INTO `scholarprofile`(`email`, `link`) VALUES (?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1,temail);
        ps.setString(2,tlink);
        ps.executeUpdate();
        conn.close();
        showAlert(Alert.AlertType.CONFIRMATION, owner, "Link add",
                "Scholar added Successfully");
        link.setText("");
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
    @FXML
    void createScBT(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CreateSchedule.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
