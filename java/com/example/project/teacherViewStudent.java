package com.example.project;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static com.example.project.TeacherHomeController.pid;

public class teacherViewStudent  implements Initializable {



    @FXML
    private Label ach;



    @FXML
    private ImageView img;

    @FXML
    private Label lan;

    @FXML
    private Label men;
    @FXML
    private ImageView a;
    @FXML
    private ImageView b;

    @FXML
    private Label papers;

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
    void createScBT(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CreateSchedule.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
    void homeBT(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("TeacherHome.fxml"));
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
            String sregSql = "SELECT * FROM `sregistration` WHERE id='" + pid + "'";
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



            String sql = "SELECT * FROM `updatep` WHERE id='" + pid + "'";
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
