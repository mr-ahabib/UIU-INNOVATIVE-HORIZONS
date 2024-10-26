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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static com.example.project.StloginController.*;


public class profile1 implements Initializable {

    @FXML
    private Button GroupB;

    @FXML
    private Button HomeB;

    @FXML
    private Label ach;

    @FXML
    private Button createGroupBT;

    @FXML
    private ImageView img;

    @FXML
    private Label lan;

    @FXML
    private Button mbt;

    @FXML
    private Label men;

    @FXML
    private Button mfB;

    @FXML
    private Label papers;

    @FXML
    private Label pd;
    @FXML
    private ImageView a;
    @FXML
    private ImageView b;
    @FXML
    private Label pub;

    @FXML
    private Button reqB;

    @FXML
    private Button requB;

    @FXML
    private Label scgpa;

    @FXML
    private Label semail;

    @FXML
    private Label sname;

    @FXML
    private Label sphone;

    @FXML
    private Button updateB;

    @FXML
    void GroupBT(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Groups.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
    void cGroupBt(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Cgroup.fxml"));
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

    @FXML
    void updateBT(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("updateProfile.fxml"));
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


        sname.setText(name);
        semail.setText(ema);
        sphone.setText(phn);
        scgpa.setText(cg);

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/uiubuddy", "root", "");
            String sql = "SELECT * FROM `updatep` WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, sid);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                papers.setText(rs.getString("paper"));
                pd.setText(rs.getString("pdetails"));
                ach.setText(rs.getString("achieve"));
                pub.setText(rs.getString("publish"));
                men.setText(rs.getString("mentor"));
                lan.setText(rs.getString("language"));

                Blob b = rs.getBlob("pic");  // Use the column name
                if (b != null) {
                    InputStream in = b.getBinaryStream();
                    Image image = new Image(in);
                    img.setImage(image);
                }

                rs.close();
                ps.close();
                conn.close();
            } else {
                System.out.println("No data found for ID: " + sid);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
}
