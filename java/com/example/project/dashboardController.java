package com.example.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class dashboardController implements Initializable {

    @FXML
    private Button bachB;

    @FXML
    private Label complete;

    @FXML
    private Label groups;

    @FXML
    private Label mentors;

    @FXML
    private Label running;

    @FXML
    private Label students;

    @FXML
    void bachBT(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("admin.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fetchStudents();
        fetchMentors();
        fetchgroups();
        fetchComplete();
        fetchRunning();
    }
    private void fetchStudents() {
        String driver = "com.mysql.cj.jdbc.Driver";
        String dbUrl = "jdbc:mysql://localhost:3306/uiubuddy";
        String userName = "root";
        String password = "";
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(dbUrl, userName, password);
            PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(id) AS counts FROM sregistration");

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String total = rs.getString("counts");


                students.setText(total);

            }

            conn.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void fetchMentors() {
        String driver = "com.mysql.cj.jdbc.Driver";
        String dbUrl = "jdbc:mysql://localhost:3306/uiubuddy";
        String userName = "root";
        String password = "";
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(dbUrl, userName, password);
            PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(email) AS counts FROM tregistration");

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String total = rs.getString("counts");


                mentors.setText(total);

            }

            conn.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void fetchgroups() {
        String driver = "com.mysql.cj.jdbc.Driver";
        String dbUrl = "jdbc:mysql://localhost:3306/uiubuddy";
        String userName = "root";
        String password = "";
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(dbUrl, userName, password);
            PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(gid) AS counts FROM groups");

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String total = rs.getString("counts");


                groups.setText(total);

            }

            conn.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void fetchComplete() {
        String driver = "com.mysql.cj.jdbc.Driver";
        String dbUrl = "jdbc:mysql://localhost:3306/uiubuddy";
        String userName = "root";
        String password = "";
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(dbUrl, userName, password);
            PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(id) AS counts FROM counseling WHERE complete='Completed'");

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String total = rs.getString("counts");


                complete.setText(total);

            }

            conn.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void fetchRunning() {
        String driver = "com.mysql.cj.jdbc.Driver";
        String dbUrl = "jdbc:mysql://localhost:3306/uiubuddy";
        String userName = "root";
        String password = "";
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(dbUrl, userName, password);
            PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(id) AS counts FROM counseling WHERE complete='Running'");

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String total = rs.getString("counts");


                running.setText(total);

            }

            conn.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
