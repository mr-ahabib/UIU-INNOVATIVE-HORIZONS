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

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.util.ArrayList;


public class StloginController {
    public  static ArrayList<User> users = new ArrayList<>();


    public static boolean matched;
    public static String phn;
    public static String sid;
    public static String name;
    public static String ema;
    public static  String cg;

    @FXML
    private PasswordField pass;

    @FXML
    private TextField userid;

    @FXML
    private Button backButton;

    @FXML
    private Button Slogin;

//    @FXML
//    void cancel(ActionEvent event) {
//
//    }

    @FXML
    void backButtonBt(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("index.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }




    @FXML
    void SloginBt(ActionEvent event) throws IOException, ClassNotFoundException, SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
        Window owner = Slogin.getScene().getWindow();
        String driver = "com.mysql.cj.jdbc.Driver";
        String dbUrl = "jdbc:mysql://localhost:3306/uiubuddy";
        String userName = "root";
        String password = "";
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(dbUrl, userName, password);

        try (conn) {
            String inputUsername = userid.getText();
            String inputPassword = pass.getText();

            String login = "SELECT * FROM `sregistration` WHERE id =?";
            try (PreparedStatement statement = conn.prepareStatement(login)) {
                statement.setString(1, inputUsername);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        name=resultSet.getString(1);
                        sid=resultSet.getString(2);
                        ema=resultSet.getString(3);
                        phn=resultSet.getString(6);
                        cg=resultSet.getString(4);

                        User newUser = new User();
                        newUser.name = name;
                        users.add(newUser);


                        String storedPasswordHash = resultSet.getString("password");
                        if (validatePassword(inputPassword, storedPasswordHash)) {

                            // Login successful, open the main application window or perform necessary actions
                            Parent root = FXMLLoader.load(getClass().getResource("profile1.fxml"));
                            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            Scene scene = new Scene(root);
                            stage.setScene(scene);
                            stage.show();
                            showAlert(Alert.AlertType.CONFIRMATION, owner, "Student Login",
                                    "Student Login Successful");
                        } else {
                            // Login failed, show an error message or handle it as per your UI requirements
                            showAlert(Alert.AlertType.ERROR, owner, "Student Login",
                                    "Invalid ID or password.");
                            userid.setText("");
                            pass.setText("");
                        }
                    } else {
                        // Login failed, show an error message or handle it as per your UI requirements
                        showAlert(Alert.AlertType.ERROR, owner, "Student Login",
                                "Invalid ID or password.");
                        userid.setText("");
                        pass.setText("");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }




        }

// Rest of the code remains the same...

    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
    private static boolean validatePassword(String originalPassword, String storedPassword)
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        String[] parts = storedPassword.split(":");
        int iterations = Integer.parseInt(parts[0]);

        byte[] salt = fromHex(parts[1]);
        byte[] hash = fromHex(parts[2]);

        PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(),
                salt, iterations, hash.length * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] testHash = skf.generateSecret(spec).getEncoded();

        int diff = hash.length ^ testHash.length;
        for(int i = 0; i < hash.length && i < testHash.length; i++)
        {
            diff |= hash[i] ^ testHash[i];
        }
        return diff == 0;
    }
    private static byte[] fromHex(String hex) throws NoSuchAlgorithmException
    {
        byte[] bytes = new byte[hex.length() / 2];
        for(int i = 0; i < bytes.length ;i++)
        {
            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }
    private static String generateStorngPasswordHash(String password)
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        int iterations = 1000;
        char[] chars = password.toCharArray();
        byte[] salt = getSalt();

        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        byte[] hash = skf.generateSecret(spec).getEncoded();
        return iterations + ":" + toHex(salt) + ":" + toHex(hash);
    }
    private static byte[] getSalt() throws NoSuchAlgorithmException
    {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    private static String toHex(byte[] array) throws NoSuchAlgorithmException
    {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);

        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
        {
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        }else{
            return hex;
        }
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
