package com.example.project;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

import static com.example.project.loginController.temail;
import static com.example.project.runGcontroller.gpid;

public class tSendController {

    @FXML
    private Button HomeB;
    @FXML
    private Button sendB;
    @FXML
    private ProgressBar pro;

    @FXML
    private Button cSchedule;

    @FXML
    private TextArea mess;
    @FXML
    private Label progressLabel;
    @FXML
    private TextArea updates;
    @FXML
    private ImageView a;
    @FXML
    private ImageView b;




    @FXML
    void completeBT(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("completeG.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void createScBT(ActionEvent event)  throws IOException{
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

    @FXML
    void runningBT(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("runningG.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void sendBT(ActionEvent event) throws IOException, SQLException, NoSuchAlgorithmException,ClassNotFoundException {
        Window owner = sendB.getScene().getWindow();
        String driver="com.mysql.cj.jdbc.Driver";
        String dbUrl="jdbc:mysql://localhost:3306/uiubuddy";
        String userName="root";
        String password="";
        Class.forName(driver);
        Connection conn= DriverManager.getConnection(dbUrl,userName,password);
        String message=mess.getText();
        String sql="INSERT INTO `tmsg`(`memail`, `gid`, `msg`) VALUES (?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1,temail);
        ps.setString(2,gpid);
        ps.setString(3,message);

        ps.executeUpdate();
        conn.close();
        showAlert(Alert.AlertType.CONFIRMATION, owner, "Message",
                "Status Send Successfully");
        mess.setText("");


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
    private void initialize() {

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
        try {
            String driver = "com.mysql.cj.jdbc.Driver";
            String dbUrl = "jdbc:mysql://localhost:3306/uiubuddy";
            String userName = "root";
            String password = "";

            Class.forName(driver);
            Connection conn = DriverManager.getConnection(dbUrl, userName, password);

            String selectSql = "SELECT `improved` FROM `progress` WHERE `memail` = ? AND `gid` = ?";
            PreparedStatement selectStmt = conn.prepareStatement(selectSql);
            selectStmt.setString(1, temail); // Assuming temail is defined elsewhere
            selectStmt.setString(2, gpid);   // Assuming gpid is defined elsewhere
            ResultSet resultSet = selectStmt.executeQuery();

            if (resultSet.next()) {
                double currentImproved = Double.parseDouble(resultSet.getString("improved"));
                pro.setProgress(currentImproved);
                double percentage = currentImproved * 100;
                progressLabel.setText(String.format("%.2f%%", percentage));
            }

            conn.close();
        } catch (SQLException | ClassNotFoundException | NumberFormatException e) {
            e.printStackTrace();
        }


        try {
            String driver = "com.mysql.cj.jdbc.Driver";
            String dbUrl = "jdbc:mysql://localhost:3306/uiubuddy";
            String userName = "root";
            String password = "";

            Class.forName(driver);
            Connection conn = DriverManager.getConnection(dbUrl, userName, password);

            String selectSql = "SELECT `umsg` FROM `updates` WHERE `gid` = ?";
            PreparedStatement selectStmt = conn.prepareStatement(selectSql);

            selectStmt.setString(1, gpid);
            ResultSet resultSet = selectStmt.executeQuery();

            StringBuilder updatesText = new StringBuilder();
            int serialNumber = 1;

            while (resultSet.next()) {
                String message = resultSet.getString("umsg");
                updatesText.append(serialNumber).append(". ").append(message).append("\n");
                serialNumber++;
            }

            updates.setText(updatesText.toString());

            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }






    @FXML
    private void agreeBT(ActionEvent event) throws IOException, NoSuchAlgorithmException, SQLException, ClassNotFoundException {
        try {
            String driver = "com.mysql.cj.jdbc.Driver";
            String dbUrl = "jdbc:mysql://localhost:3306/uiubuddy";
            String userName = "root";
            String password = "";

            Class.forName(driver);
            Connection conn = DriverManager.getConnection(dbUrl, userName, password);

            String selectSql = "SELECT `improved` FROM `progress` WHERE `memail` = ? AND `gid` = ?";
            PreparedStatement selectStmt = conn.prepareStatement(selectSql);
            selectStmt.setString(1, temail);
            selectStmt.setString(2, gpid);
            ResultSet resultSet = selectStmt.executeQuery();

            double increasePercentage = 0.25; // 25% increase

            if (resultSet.next()) {
                String currentImprovedStr = resultSet.getString("improved");
                double currentImproved = Double.parseDouble(currentImprovedStr);
                double newImproved = currentImproved + (currentImproved * increasePercentage); // Increase by 25%

                String updateSql = "UPDATE `progress` SET `improved` = ? WHERE `memail` = ? AND `gid` = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                updateStmt.setString(1, String.valueOf(newImproved)); // Convert double to string
                updateStmt.setString(2, temail);
                updateStmt.setString(3, gpid);
                updateStmt.executeUpdate();

                pro.setProgress(pro.getProgress() + increasePercentage);

                showAlert(Alert.AlertType.INFORMATION, pro.getScene().getWindow(),
                        "Progress Updated", "Progress updated by 25%.");
            } else {
                // Insert initial data into the progress table
                String insertSql = "INSERT INTO `progress` (`memail`, `gid`, `improved`) VALUES (?, ?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                insertStmt.setString(1, temail);
                insertStmt.setString(2, gpid);
                insertStmt.setString(3, String.valueOf(increasePercentage));
                insertStmt.executeUpdate();

                pro.setProgress(increasePercentage);

                showAlert(Alert.AlertType.INFORMATION, pro.getScene().getWindow(),
                        "Progress Updated", "Progress initialized with 25%.");
            }

            conn.close();
        } catch (SQLException | ClassNotFoundException | NumberFormatException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, pro.getScene().getWindow(),
                    "Error", "An error occurred while updating progress.");
        }
    }






    @FXML
    private void disagreeBT(ActionEvent event) throws IOException, NoSuchAlgorithmException, SQLException, ClassNotFoundException {
        try {

            String driver = "com.mysql.cj.jdbc.Driver";
            String dbUrl = "jdbc:mysql://localhost:3306/uiubuddy";
            String userName = "root";
            String password = "";


            Class.forName(driver);
            Connection conn = DriverManager.getConnection(dbUrl, userName, password);


            String selectSql = "SELECT `improved` FROM `progress` WHERE `memail` = ? AND `gid` = ?";
            PreparedStatement selectStmt = conn.prepareStatement(selectSql);
            selectStmt.setString(1, temail);
            selectStmt.setString(2, gpid);
            ResultSet resultSet = selectStmt.executeQuery();

            if (resultSet.next()) {
                String currentImprovedStr = resultSet.getString("improved");
                double currentImproved = Double.parseDouble(currentImprovedStr);
                double newImproved = currentImproved * 0.9;
                String updateSql = "UPDATE `progress` SET `improved` = ? WHERE `memail` = ? AND `gid` = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                updateStmt.setString(1, String.valueOf(newImproved)); // Convert double to string
                updateStmt.setString(2, temail);
                updateStmt.setString(3, gpid);
                updateStmt.executeUpdate();


                pro.setProgress(newImproved);

                double percentage = newImproved * 100;
                progressLabel.setText(String.format("%.2f%%", percentage));

                showAlert(Alert.AlertType.INFORMATION, pro.getScene().getWindow(),
                        "Progress Updated", "Progress decreased by 10%.");
            } else {
                // Handle the case where no progress data exists
                // ...
            }

            conn.close();
        } catch (SQLException | ClassNotFoundException | NumberFormatException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, pro.getScene().getWindow(),
                    "Error", "An error occurred while updating progress.");
        }
    }



}
