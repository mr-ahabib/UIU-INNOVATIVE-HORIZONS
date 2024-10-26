package com.example.project;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ResourceBundle;

import static com.example.project.reqController.gid;

public class smsgController implements Initializable {

    @FXML
    private Button GroupB;

    @FXML
    private Button createGroupBT;
    @FXML
    private ImageView a;
    @FXML
    private ImageView b;

    @FXML
    private Button mbt;

    @FXML
    private TextArea mesg;

    @FXML
    private Button mfB;

    @FXML
    private Button reqB;

    @FXML
    private Button requB;

    @FXML
    private TextField msg;

    @FXML
    private ProgressBar pro;

    @FXML
    private Label progressLabel;
    @FXML
    private Button sendB;
    @FXML
    private Button submitB;

    @FXML
    private TextArea umsg;


    @FXML
    void GroupBT(ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Groups.fxml"));
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
    void cGroupBt(ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Cgroup.fxml"));
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
    void requBT(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("GroupRequest.fxml"));
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        fetchAndPopulateMessages();
        fetchAndPopulateProgress();
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
    private void fetchAndPopulateMessages() {
        String driver = "com.mysql.cj.jdbc.Driver";
        String dbUrl = "jdbc:mysql://localhost:3306/uiubuddy";
        String userName = "root";
        String password = "";
        int serialNumber = 1;

        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(dbUrl, userName, password);

            Statement statement = conn.createStatement();

            String query = "SELECT * FROM tmsg WHERE gid='" + gid + "'";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String message = resultSet.getString("msg");

                mesg.appendText(serialNumber + ". "  + message + "\n");

                serialNumber++;
            }

            resultSet.close();
            statement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchAndPopulateProgress() {
        String driver = "com.mysql.cj.jdbc.Driver";
        String dbUrl = "jdbc:mysql://localhost:3306/uiubuddy";
        String userName = "root";
        String password = "";

        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(dbUrl, userName, password);

            Statement statement = conn.createStatement();

            // Query to fetch progress data based on your requirements
            String query = "SELECT * FROM progress WHERE  gid='" + gid + "'";
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                String improvedValue = resultSet.getString("improved");
                double progressValue = Double.parseDouble(improvedValue);

                // Convert the progress value to a percentage
                double progressPercentage = progressValue * 100;

                // Update progressLabel and pro components based on the calculated percentage
                progressLabel.setText( String.format("%.2f%%", progressPercentage));
                pro.setProgress(progressValue);
            }

            resultSet.close();
            statement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    void sendBT(ActionEvent event) {

    }




    @FXML
    void submitBT(ActionEvent event) throws IOException, SQLException, NoSuchAlgorithmException,ClassNotFoundException {
        Window owner = submitB.getScene().getWindow();
        String driver = "com.mysql.cj.jdbc.Driver";
        String dbUrl = "jdbc:mysql://localhost:3306/uiubuddy";
        String userName = "root";
        String password = "";
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(dbUrl, userName, password);
        String upmsg=umsg.getText();
        String sql = "INSERT INTO `updates`(`gid`, `umsg`) VALUES (?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, gid);
        ps.setString(2,upmsg);
        ps.executeUpdate();

        ps.close();
        conn.close();
        showAlert(Alert.AlertType.CONFIRMATION, owner, "Update",
                "Update submitted Successfully");
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
