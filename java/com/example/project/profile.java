package com.example.project;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import static com.example.project.StloginController.*;


public class profile implements Initializable {

    @FXML
    private Button mbt;
    @FXML
    private Button GroupB;

    @FXML
    private Button createGroupBT;
    @FXML
    private Button mfB;
    @FXML
    private Button reqB;

    @FXML
    private Button requB;

    @FXML
    private TextField email;
    @FXML
    private TextField sname;
    @FXML
    private TextField id;
    @FXML
    private TextField phone;


    @FXML
    private Label de;
    @FXML
    private Label fp;
    @FXML
    private Label st;
    @FXML
    private Label na;


    @FXML
    private ImageView a;
    @FXML
    private ImageView b;

    @FXML
    private Button logout;





    @FXML
    void cGroupBt(ActionEvent event) throws IOException {
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




    @Override
    public void initialize(URL url, ResourceBundle rb) {
        id.setText(sid);
        sname.setText(name);
        email.setText(ema);
        phone.setText(phn);


        fetchCounselingData();


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
    }

    private void fetchCounselingData() {
        String driver = "com.mysql.cj.jdbc.Driver";
        String dbUrl = "jdbc:mysql://localhost:3306/uiubuddy";
        String userName = "root";
        String password = "";

        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(dbUrl, userName, password);
            PreparedStatement stmt = conn.prepareStatement("SELECT c.memail, c.fydp, c.day, c.status, m.mname FROM counseling c JOIN members m ON c.id = m.g_id WHERE m.mid = '" + sid + "'");

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String email = rs.getString("memail");
                String f = rs.getString("fydp");
                String day = rs.getString("day");
                String stat = rs.getString("status");

                de.setText(email);
                fp.setText(f);
                na.setText(day);
                st.setText(stat);
            }

            conn.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    void MentorsBT(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Mentor.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }




    @FXML
    void reqBT(ActionEvent event) throws IOException{
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

    @FXML
    void mProfileBT(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Profile1.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void logoutBT(ActionEvent event)  throws IOException{
        Stage currentStage = (Stage) logout.getScene().getWindow();
        currentStage.close();
        Platform.exit();
    }

}
