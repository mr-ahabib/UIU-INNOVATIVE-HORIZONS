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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.example.project.StloginController.sid;

public class reqController implements Initializable {

    @FXML
    private Button viewB;

    public static String gid;
    public static String psid;
    @FXML
    private Button go;
    @FXML
    private ImageView a;
    @FXML
    private ImageView b;


    @FXML
    private TableView<membersview> members;

    @FXML
    private TableColumn<membersview, String> mname;
    @FXML
    private TableColumn<membersview, String> mid;
    @FXML
    private TableColumn<membersview, String> g_id;
    @FXML
    private TableColumn<membersview, String> memail;

    ObservableList<membersview> list = FXCollections.observableArrayList();


    @FXML
    private Button GroupB;

    @FXML
    private Button HomeB;

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
    void requBT(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("GroupRequest.fxml"));
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
    void homeBT(ActionEvent event)  throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("profile.fxml"));
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


        members.setRowFactory(tv -> {
            return new TableRow<membersview>() {
                @Override
                protected void updateItem(membersview item, boolean empty) {
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
        String driver = "com.mysql.cj.jdbc.Driver";
        String dbUrl = "jdbc:mysql://localhost:3306/uiubuddy";
        String userName = "root";
        String password = "";
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(dbUrl, userName, password);
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM `members` WHERE status='Accepted' AND g_id IN (SELECT g_id FROM `members` WHERE mid='" + sid + "')");

            while (rs.next()) {
                list.add(new membersview(rs.getString("mname"), rs.getString("mid"), rs.getString("g_id"), rs.getNString("memail")));
            }
            conn.close();
            rs.close();
        } catch (Exception e) {

        }


        mname.setCellValueFactory(new PropertyValueFactory<membersview, String>("mname"));
        mid.setCellValueFactory(new PropertyValueFactory<membersview, String>("mid"));
        g_id.setCellValueFactory(new PropertyValueFactory<membersview, String>("g_id"));
        memail.setCellValueFactory(new PropertyValueFactory<membersview, String>("memail"));


        members.setItems(list);
        members.setOnMouseClicked(e -> {


                    events();
                }

        );


    }

    private void events() {
        for (membersview pick : members.getSelectionModel().getSelectedItems()) {
//            for (int i = 1; i <= 1; i++)

            gid = pick.getG_id();
            psid=pick.getMid();


        }


    }
    @FXML
    void goBT(ActionEvent event) throws IOException, SQLException, NoSuchAlgorithmException,ClassNotFoundException {
        String driver = "com.mysql.cj.jdbc.Driver";
        String databaseurl = "jdbc:mysql://localhost:3306/uiubuddy";
        String username = "root";
        String passwords = "";
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(databaseurl, username, passwords);
        Parent root = FXMLLoader.load(getClass().getResource("counMentors.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        Window owner = go.getScene().getWindow();
        showAlert(Alert.AlertType.CONFIRMATION, owner, "Group ID", "Group ID Picked");
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
    void viewBT(ActionEvent event) throws IOException,NoSuchAlgorithmException,SQLException,ClassNotFoundException {
        String driver = "com.mysql.cj.jdbc.Driver";
        String databaseurl = "jdbc:mysql://localhost:3306/uiubuddy";
        String username = "root";
        String passwords = "";
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(databaseurl, username, passwords);

        Parent root = FXMLLoader.load(getClass().getResource("sView.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        Window owner = viewB.getScene().getWindow();

    }
    @FXML
    void cRoomBT(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("chat.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    @FXML
    void notiBT(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("smsgView.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

}
