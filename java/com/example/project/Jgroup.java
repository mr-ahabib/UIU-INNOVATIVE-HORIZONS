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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static com.example.project.Groups.s;
import static com.example.project.StloginController.sid;

public class Jgroup  implements Initializable {

    @FXML
    private Button GroupB;
    @FXML
    private TextField id;
    @FXML
    private ImageView img;

    @FXML
    private Button HomeB;
    @FXML
    private Button mbt;
    @FXML
    private ImageView a;
    @FXML
    private ImageView b;

    @FXML
    private TextField memail;

    @FXML
    private TextField mid;

    @FXML
    private TextField mname;

    @FXML
    private Button createGroupBT;

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
    void MentorsBT(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Mentor.fxml"));
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
            String sql = "SELECT * FROM `updatep` WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, sid);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Blob b = rs.getBlob("pic");
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

            }catch (SQLException e) {
            e.printStackTrace();

        }
        }


     @FXML
    void Submit(ActionEvent event) throws IOException, SQLException,ClassNotFoundException{
         Window owner = mbt.getScene().getWindow();
         String driver="com.mysql.cj.jdbc.Driver";
         String dbUrl="jdbc:mysql://localhost:3306/uiubuddy";
         String userName="root";
         String password="";
         Class.forName(driver);
         Connection conn= DriverManager.getConnection(dbUrl,userName,password);
         String name=mname.getText();
         String id=mid.getText();
         String mail=memail.getText();
         String sql="INSERT INTO `members`(`g_id`, `mname`, `mid`, `memail`,`status`) VALUES (?,?,?,?,?)";
         PreparedStatement ps = conn.prepareStatement(sql);
         ps.setString(1,s);
         ps.setString(2,name);
         ps.setString(3,id);
         ps.setString(4,mail);
         ps.setString(5,"Pending");
         ps.executeUpdate();
         conn.close();
         showAlert(Alert.AlertType.CONFIRMATION, owner, "Group Join",
                 "Group Joined Successfully");
         mname.setText("");
         mid.setText("");
         memail.setText("");

    }
    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
}
