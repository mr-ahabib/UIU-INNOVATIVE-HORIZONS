package com.example.project;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static com.example.project.reqController.psid;


public class sViewController implements Initializable {

    @FXML
    private Button GroupB;

    @FXML
    private Button HomeB;

    @FXML
    private Button MentorB;

    @FXML
    private Label ach;

    @FXML
    private Button createGroupBT;

    @FXML
    private ImageView img;

    @FXML
    private Label lan;

    @FXML
    private Label men;

    @FXML
    private Label papers;
    @FXML
    private ImageView a;
    @FXML
    private ImageView b;


    @FXML
    private Label pd;

    @FXML
    private Label pub;

    @FXML
    private Label scgpa;

    @FXML
    private Label semail;

    @FXML
    private Label sname;

    @FXML
    private Label sphone;

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

    @FXML
    void homeBT(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("profile.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void MentorsBT(ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Mentor.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void mProfileBT(ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Profile1.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void reqBT(ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("requests.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void requBT(ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("GroupRequest.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
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
        String driver = "com.mysql.cj.jdbc.Driver";
        String dbUrl = "jdbc:mysql://localhost:3306/uiubuddy";
        String userName = "root";
        String password = "";

        try {
            Connection conn = DriverManager.getConnection(dbUrl, userName, password);


                // Fetch data from sregistration table
                String sregSql = "SELECT * FROM `sregistration` WHERE id='" + psid + "'";
                PreparedStatement sregPs = conn.prepareStatement(sregSql);
                ResultSet sregRs = sregPs.executeQuery();

                if (sregRs.next()) {
                    sname.setText(sregRs.getString("name"));
                    semail.setText(sregRs.getString("email"));
                    sphone.setText(sregRs.getString("phone"));
                    scgpa.setText(sregRs.getString("cgpa"));
                }

                sregRs.close();
                sregPs.close();









            String sql = "SELECT * FROM `updatep` WHERE id='" + psid + "'";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                papers.setText(rs.getString("paper"));
                pd.setText(rs.getString("pdetails"));
                ach.setText(rs.getString("achieve"));
                pub.setText(rs.getString("publish"));
                men.setText(rs.getString("mentor"));
                lan.setText(rs.getString("language"));

                Blob b = rs.getBlob("pic");
                if (b != null) {
                    InputStream in = b.getBinaryStream();
                    javafx.scene.image.Image image = new javafx.scene.image.Image(in);
                    img.setImage(image);
                }
            }

            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }
    }
}
