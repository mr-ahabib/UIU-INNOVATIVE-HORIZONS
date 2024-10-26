package com.example.project;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.util.ResourceBundle;

import static com.example.project.StloginController.*;

public class Cgroup implements Initializable {

    @FXML
    private ImageView a;
    @FXML
    private ImageView b;

    @FXML
    private TableView<myGroups> CreatedGroup;
    @FXML
    private TableColumn<myGroups,String> gid;
    @FXML
    private TableColumn<myGroups, String> gname;

    @FXML
    private Button cbt;


    @FXML
    private TextField gn;
    @FXML
    private TextField stid;
    @FXML
    private Button HomeB;
    @FXML
    private TextField topic;

    @FXML
    private Button GroupB;
    @FXML
    private TextField id;
    ObservableList<myGroups> list2 = FXCollections.observableArrayList();
    @FXML
    private Button createGroupBT;
    @FXML
    void CgroupBt(ActionEvent event) throws IOException {
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

        CreatedGroup.setRowFactory(tv -> {
            return new TableRow<myGroups>() {
                @Override
                protected void updateItem(myGroups item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setStyle("");
                    } else {
                        setMinHeight(50);
                        setPrefHeight(50);
                        setMaxHeight(50);
                    }
                }
            };
        });

        //table data
        String driver = "com.mysql.cj.jdbc.Driver";
        String dbUrl = "jdbc:mysql://localhost:3306/uiubuddy";
        String userName = "root";
        String password = "";

        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(dbUrl, userName, password);
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM `groups` WHERE gid="+sid+"");
            while (rs.next()) {
                list2.add(new myGroups(rs.getString("gid"), rs.getString("gname")));
            }
            conn.close();
            rs.close();
        } catch (Exception e) {

        }
        gid.setCellValueFactory(new PropertyValueFactory<myGroups, String>("gid"));
        gname.setCellValueFactory(new PropertyValueFactory<myGroups, String>("gname"));

        CreatedGroup.setItems(list2);






    }






@FXML
    void create(ActionEvent event) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException, InvalidKeySpecException{
        Window owner = cbt.getScene().getWindow();
        String driver="com.mysql.cj.jdbc.Driver";
        String dbUrl="jdbc:mysql://localhost:3306/uiubuddy";
        String userName="root";
        String password="";
        Class.forName(driver);
        Connection conn= DriverManager.getConnection(dbUrl,userName,password);
        String cname=gn.getText();
        String cid=stid.getText();
        String ct=topic.getText();
        String create="INSERT INTO `groups`(`gid`, `gname`, `ftopic`) VALUES (?,?,?)";
        PreparedStatement ps = conn.prepareStatement(create);
        ps.setString(1,cid);
        ps.setString(2,cname);
        ps.setString(3,ct);
    ps.executeUpdate();
        String join="INSERT INTO `members`(`g_id`, `mname`, `mid`, `memail`,`status`) VALUES (?,?,?,?,?)";
    PreparedStatement p = conn.prepareStatement(join);
    p.setString(1,cid);
    p.setString(2,name);
    p.setString(3,sid);
    p.setString(4,ema);
    p.setString(5,"Accepted");

    p.executeUpdate();
        conn.close();

        showAlert(Alert.AlertType.CONFIRMATION, owner, "Group Create",
                "Create Group Successfully");
        gn.setText("");
        stid.setText("");
        topic.setText("");


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

