package com.example.project;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.example.project.StloginController.sid;

public class uProfile  implements Initializable {
    @FXML
    private Button backB;

    @FXML
    private TextField language;

    @FXML
    private TextField mentor;

    @FXML
    private TextField pDetails;

    @FXML
    private TextField paper;

    @FXML
    private TextField achieve;

    @FXML
    private TextField pub;
    @FXML
    private ImageView a;
    @FXML
    private ImageView b;

    @FXML
    private Button saveB;

    @FXML
    private Button picB;
    @FXML
    private TextField uachie;

    @FXML
    private TextField ucgpa;

    @FXML
    private TextField ulan;

    @FXML
    private TextField uphn;
    @FXML
    private Button uphnB;

    @FXML
    private Button ulanB;
    @FXML
    private Button ucgB;
    @FXML
    private Button uachB;

    private String imagepath;  // Add this line

    @FXML
    void backBT(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("profile1.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void picBT(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File");
        File file = chooser.showOpenDialog(new Stage());
        if (file != null) {
            imagepath = file.getPath();
        }
    }

    @FXML
    void saveBT(ActionEvent event) throws IOException, SQLException, FileNotFoundException, ClassNotFoundException
    {

        InputStream in = new FileInputStream(imagepath);

        Window owner = saveB.getScene().getWindow();
        String driver = "com.mysql.cj.jdbc.Driver";
        String dbUrl = "jdbc:mysql://localhost:3306/uiubuddy";
        String userName = "root";
        String password = "";

        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(dbUrl, userName, password);

            String selectedLanguage = language.getText();
            String selectedMentor = mentor.getText();
            String selectedPDetails = pDetails.getText();
            String selectedPaper = paper.getText();
            String selectedAchieve = achieve.getText();
            String selectedPub = pub.getText();

            String sql = "INSERT INTO `updatep`(`id`, `paper`, `publish`, `mentor`, `pdetails`, `language`, `achieve`, `pic`) VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, sid);
            ps.setString(2, selectedPaper);
            ps.setString(3, selectedPub);
            ps.setString(4, selectedMentor);
            ps.setString(5, selectedPDetails);
            ps.setString(6, selectedLanguage);
            ps.setString(7, selectedAchieve);
            ps.setBlob(8, in);

            // Handle the image insertion as a Blob





            ps.executeUpdate();

            ps.close();
            conn.close();
            showAlert(Alert.AlertType.CONFIRMATION, owner, "Update",
                    "Information Added Successfully");
            language.setText("");
            mentor.setText("");
            pub.setText("");
            pDetails.setText("");
            achieve.setText("");
            paper.setText("");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }
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
    void uachBT(ActionEvent event) throws IOException, SQLException, NoSuchAlgorithmException, ClassNotFoundException {
        Window owner = uachB.getScene().getWindow();
        String driver = "com.mysql.cj.jdbc.Driver";
        String dbUrl = "jdbc:mysql://localhost:3306/uiubuddy";
        String userName = "root";
        String password = "";


        String achiev = uachie.getText();

        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(dbUrl, userName, password);

            String sql = "UPDATE `updatep` SET `achieve` = ? WHERE id = '" + sid + "'";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, achiev);


            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Record updated successfully.");
            } else {
                System.out.println("No records were updated.");
            }
             ps.executeUpdate();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void ucgBT(ActionEvent event) {
        Window owner = ucgB.getScene().getWindow();
        String driver = "com.mysql.cj.jdbc.Driver";
        String dbUrl = "jdbc:mysql://localhost:3306/uiubuddy";
        String userName = "root";
        String password = "";


        String ucgp = ucgpa.getText();

        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(dbUrl, userName, password);

            String sql = "UPDATE `sregistration`  SET `cgpa`  = ? WHERE id = '" + sid + "'";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,ucgp );


            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Record updated successfully.");
            } else {
                System.out.println("No records were updated.");
            }

            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void ulanBT(ActionEvent event) {
        Window owner = ulanB.getScene().getWindow();
        String driver = "com.mysql.cj.jdbc.Driver";
        String dbUrl = "jdbc:mysql://localhost:3306/uiubuddy";
        String userName = "root";
        String password = "";


        String ulanguage = ulan.getText();

        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(dbUrl, userName, password);

            String sql = "UPDATE `updatep` SET `language` = ? WHERE id = '" + sid + "'";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ulanguage);


            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Record updated successfully.");
            } else {
                System.out.println("No records were updated.");
            }

            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void uphnBT(ActionEvent event) {
        Window owner = uachB.getScene().getWindow();
        String driver = "com.mysql.cj.jdbc.Driver";
        String dbUrl = "jdbc:mysql://localhost:3306/uiubuddy";
        String userName = "root";
        String password = "";


        String uphone = uphn.getText();

        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(dbUrl, userName, password);

            String sql = "UPDATE `sregistration`  SET `phone`  = ? WHERE id = '" + sid + "'";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, uphone);


            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Record updated successfully.");
            } else {
                System.out.println("No records were updated.");
            }
            ps.executeUpdate();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
}
