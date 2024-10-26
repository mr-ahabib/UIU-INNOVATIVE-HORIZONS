package com.example.project;

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
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;



public class Admin {
    //student data


    @FXML
    private Button back;
    @FXML
    private Button st;


    @FXML
    private TextField batch;

    @FXML
    private TextField cgpa;

    @FXML
    private TextField sdept;

    @FXML
    private TextField semail;

    @FXML
    private TextField sid;

    @FXML
    private TextField sname;

    @FXML
    private PasswordField spass;

    @FXML
    private TextField sphone;


    //teacher data
    @FXML
    private TextField tdept;

    @FXML
    private TextField temail;

    @FXML
    private TextField tname;

    @FXML
    private TextField tpass;

    @FXML
    private TextField tphone;
    @FXML
    private TextField fydp;


    @FXML
    private Button tr;




    @FXML
    void backB(ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void student(ActionEvent event) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
        Window owner = st.getScene().getWindow();
        String driver="com.mysql.cj.jdbc.Driver";
        String dbUrl="jdbc:mysql://localhost:3306/uiubuddy";
        String userName="root";
        String password="";
        Class.forName(driver);
        Connection conn= DriverManager.getConnection(dbUrl,userName,password);
        String name=sname.getText();
        String id=sid.getText();
        String dept=sdept.getText();
        String bat=batch.getText();
        String gpa=cgpa.getText();

        String pa=spass.getText();
        String paw
                = generateStorngPasswordHash(pa);
        String ph=sphone.getText();
        String em=semail.getText();
        String sql="INSERT INTO `sregistration`(`name`, `id`, `email`, `cgpa`, `batch`, `phone`, `dept`, `password`) VALUES (?,?,?,?,?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1,name);
        ps.setString(2,id);
        ps.setString(3,em);
        ps.setString(4,gpa);
        ps.setString(5,bat);
        ps.setString(6,ph);
        ps.setString(7,dept);
        ps.setString(8,paw);
        ps.executeUpdate();
        conn.close();

        showAlert(Alert.AlertType.CONFIRMATION, owner, "Student Signup",
                "Student Added Successfully");
        sname.setText("");
        sid.setText("");
        semail.setText("");
        sdept.setText("");
        sphone.setText("");
        spass.setText("");
        cgpa.setText("");
        batch.setText("");




    }

    @FXML
    void teacher(ActionEvent event) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
        Window owner = tr.getScene().getWindow();
        String driver="com.mysql.cj.jdbc.Driver";
        String dbUrl="jdbc:mysql://localhost:3306/uiubuddy";
        String userName="root";
        String password="";
        Class.forName(driver);
        Connection conn= DriverManager.getConnection(dbUrl,userName,password);
        String name=tname.getText();
        String email=temail.getText();
        String phone=tphone.getText();
        String dep=tdept.getText();


        String pas=tpass.getText();
        String paw= generateStorngPasswordHash(pas);
        String fyd=fydp.getText();

        String sql1="INSERT INTO `tregistration`(`name`, `email`, `Room`, `dept`, `fydp`, `pass`) VALUES (?,?,?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql1);
        ps.setString(1,name);
        ps.setString(2,email);
        ps.setString(3,phone);
        ps.setString(4,dep);
        ps.setString(5,fyd);
        ps.setString(6,pas);

        ps.executeUpdate();
        conn.close();
        showAlert(Alert.AlertType.CONFIRMATION, owner, "Teacher Signup",
                "Teacher Added Successfully");
        tname.setText("");
        temail.setText("");
        tpass.setText("");
        tphone.setText("");
        tdept.setText("");
        fydp.setText("");

    }

    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
    //password hash
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






}
