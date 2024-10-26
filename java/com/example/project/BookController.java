package com.example.project;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
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
import java.sql.*;
import java.util.ResourceBundle;

import static com.example.project.MentorController.fy;
import static com.example.project.MentorController.meid;
import static com.example.project.StloginController.name;
import static com.example.project.StloginController.sid;
import static com.example.project.reqController.gid;

public class BookController implements Initializable {
    @FXML
    private Button confirm;

    @FXML
    private Button scholarB;

    @FXML
    private TextField room;

    @FXML
    private TextField tdept;

    @FXML
    private TextField temail;

    @FXML
    private TextField tname;

    public static String cday;

    @FXML
    private TableView<counsellingView> counselling;
    @FXML
    private TableColumn<counsellingView, String> day;
    @FXML
    private TableColumn<counsellingView, String> Start;
    @FXML
    private TableColumn<counsellingView, String> End;
    ObservableList<counsellingView> list = FXCollections.observableArrayList();
    @FXML
    private Button GroupB;

    @FXML
    private Button HomeB;
    @FXML
    private ImageView a;
    @FXML
    private ImageView b;
    @FXML
    private Button btnDate;

    @FXML
    private Button createGroupBT;
    private Stage stage;
    @FXML
    private DatePicker dtPicker;

    @FXML
    private Label lbDate;

    @FXML
    void CgroupBt(ActionEvent event) {

    }

    @FXML
    void GroupBT(ActionEvent event) {

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
    void onDate(ActionEvent event) {

    }



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

        counselling.setRowFactory(tv -> {
            return new TableRow<counsellingView>() {
                @Override
                protected void updateItem(counsellingView item, boolean empty) {
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

            ResultSet rsTRegistration = conn.createStatement().executeQuery("SELECT * FROM `tregistration` WHERE email='" + meid + "'");
            if (rsTRegistration.next()) {
                tname.setText(rsTRegistration.getString("name"));
                temail.setText(rsTRegistration.getString("email"));
                tdept.setText(rsTRegistration.getString("dept"));
                room.setText(rsTRegistration.getString("Room"));
            }

            conn.close();
            rsTRegistration.close();
//            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM `schedule` WHERE email='" + meid + "'");
//            while (rs.next()) {
//                list.add(new counsellingView(rs.getString("day"), rs.getString("start"), rs.getString("end")));
//            }
//            conn.close();
//            rs.close();
        } catch (Exception e) {
e.printStackTrace();
        }
        Platform.runLater(() -> {
            try {
                Connection conn = DriverManager.getConnection(dbUrl, userName, password);
                // Your database-related code here
                ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM `schedule` WHERE email='" + meid + "'");
                while (rs.next()) {
                    list.add(new counsellingView(rs.getString("day"), rs.getString("start"), rs.getString("end")));
                }
                conn.close();
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        day.setCellValueFactory(new PropertyValueFactory<counsellingView, String>("day"));
        Start.setCellValueFactory(new PropertyValueFactory<counsellingView, String>("start"));
        End.setCellValueFactory(new PropertyValueFactory<counsellingView, String>("end"));

        counselling.setItems(list);
        counselling.setOnMouseClicked(e -> {


                    events();
                }

        );
    }
    private void events() {
        for (counsellingView pick : counselling.getSelectionModel().getSelectedItems()) {
//            for (int i = 1; i <= 1; i++)
            cday = pick.getDay();


        }

    }


    @FXML
    void confirmBT(ActionEvent event) throws IOException, SQLException, NoSuchAlgorithmException,ClassNotFoundException {

        String driver="com.mysql.cj.jdbc.Driver";
        String dbUrl="jdbc:mysql://localhost:3306/uiubuddy";
        String userName="root";
        String password="";
        Class.forName(driver);
        Connection conn= DriverManager.getConnection(dbUrl,userName,password);
        String sql="INSERT INTO `counseling`(`name`, `id`, `gid`, `fydp`, `memail`, `day`, `status`,`complete`) VALUES (?,?,?,?,?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1,name);
        ps.setString(2,sid);
        ps.setString(3,gid);
        ps.setString(4,fy);
        ps.setString(5,meid);
        ps.setString(6,cday);
        ps.setString(7,"Pending");
        ps.setString(8,"");

        ps.executeUpdate();
        conn.close();
        Window owner = confirm.getScene().getWindow();
        showAlert(Alert.AlertType.CONFIRMATION, owner, "Counselling Request", "Counselling Request Sent Successfully");
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
    void scholarBT(ActionEvent event) throws IOException, SQLException, NoSuchAlgorithmException, ClassNotFoundException {
        String driver = "com.mysql.cj.jdbc.Driver";
        String dbUrl = "jdbc:mysql://localhost:3306/uiubuddy";
        String userName = "root";
        String password = "";

        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(dbUrl, userName, password);
            ResultSet rs = conn.createStatement().executeQuery("SELECT link FROM `scholarprofile` WHERE email='" + meid + "'");

            if (rs.next()) {
                String link = rs.getString("link");

                FXMLLoader loader = new FXMLLoader(getClass().getResource("webview.fxml"));
                Parent root = loader.load();
                webViewController webController = loader.getController();
                webController.loadURL(link);
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}





