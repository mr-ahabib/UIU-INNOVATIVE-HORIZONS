package com.example.project;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.*;

public class loginController {
    public static String temail;
    public static String tname;
    public static String fydp;

    @FXML
    private PasswordField pass;

    @FXML
    private TextField username;

    @FXML
    private Button backButton;

    @FXML
    private Button loginbt;

    @FXML
    void tloginBT(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        Window owner = loginbt.getScene().getWindow();
        String driver = "com.mysql.cj.jdbc.Driver";
        String dbUrl = "jdbc:mysql://localhost:3306/uiubuddy";
        String userName = "root";
        String password = "";
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(dbUrl, userName, password);

        String enteredUsername = username.getText();
        String enteredPassword = pass.getText();


        if (enteredUsername.isEmpty() || enteredPassword.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!", "Please enter both username and password.");
            return;
        }


        String query = "SELECT * FROM `tregistration` WHERE email = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, enteredUsername);

        ResultSet resultSet = stmt.executeQuery();


        if (resultSet.next()) {

            temail = resultSet.getString(2);
            tname = resultSet.getString(1);
            fydp = resultSet.getString(5);
            Parent root = FXMLLoader.load(getClass().getResource("TeacherHome.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            showAlert(Alert.AlertType.CONFIRMATION, owner, "Teacher Login",
                    "Login Successfully");

        } else {
            showAlert(Alert.AlertType.ERROR, owner, "Login Failed!", "Invalid username or password.");
        }


        resultSet.close();
        stmt.close();
        conn.close();
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
    void backButtonBt(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("index.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    private ImageView a;

    @FXML
    private ImageView b;

    @FXML
    private ImageView c;

    @FXML
    private ImageView d;

    public void initialize() {
        applyTranslateAnimation(a, 0, 50, 3);
        applyTranslateAnimation(b, 0, -50, 3);
        applyTranslateAnimation(c, 0, 50, 3);
        applyTranslateAnimation(d, 0, -50, 3);
    }

    private void applyTranslateAnimation(ImageView imageView, double fromY, double toY, double durationSeconds) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(durationSeconds), imageView);
        translateTransition.setFromY(fromY);
        translateTransition.setToY(toY);
        translateTransition.setCycleCount(TranslateTransition.INDEFINITE);
        translateTransition.setAutoReverse(true);
        translateTransition.play();
    }


}
