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
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.example.project.StloginController.sid;

public class GroupRequestController implements Initializable {
    public static String smid;

    @FXML
    private TableView<gRequestView> groupRequest;

    @FXML
    private TableColumn<gRequestView, String> mname;

    @FXML
    private TableColumn<gRequestView, String> mid;


    @FXML
    private TableColumn<gRequestView, String> memail;

    @FXML
    private TableColumn<gRequestView, String> status;
    ObservableList<gRequestView> list = FXCollections.observableArrayList();

    @FXML
    private Button acceptBT;


    @FXML
    private Button deleteBT;

    @FXML
    private Button viewB;

    @FXML
    private Button reqB;

    @FXML
    private Button requB;

    @FXML
    private ImageView a;
    @FXML
    private ImageView b;


    @FXML
    private Button GroupB;

    @FXML
    private Button HomeB;

    @FXML
    private Button MentorB;

    @FXML
    private Button ViewB1;




    @FXML
    void cGroupBt(ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Cgroup.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void GroupBT(ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Groups.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void homeBT(ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("profile.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void mentorBT(ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Mentor.fxml"));
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

        groupRequest.setRowFactory(tv -> {
            return new TableRow<gRequestView>() {
                @Override
                protected void updateItem(gRequestView item, boolean empty) {
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
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM `members` WHERE status='Pending' AND g_id IN (SELECT g_id FROM `members` WHERE mid='" + sid + "')");

            while (rs.next()) {
                list.add(new gRequestView(rs.getString("mname"), rs.getString("mid"), rs.getString("memail"), rs.getNString("status")));
            }
            conn.close();
            rs.close();
        } catch (Exception e) {

        }
        mname.setCellValueFactory(new PropertyValueFactory<gRequestView, String>("mname"));
        mid.setCellValueFactory(new PropertyValueFactory<gRequestView, String>("mid"));
        memail.setCellValueFactory(new PropertyValueFactory<gRequestView, String>("memail"));
        status.setCellValueFactory(new PropertyValueFactory<gRequestView, String>("status"));


        groupRequest.setItems(list);
        groupRequest.setOnMouseClicked(e -> {


                    events();
                }

        );
    }
    private void events() {
        for (gRequestView pick : groupRequest.getSelectionModel().getSelectedItems()) {
//            for (int i = 1; i <= 1; i++)

            smid = pick.getMid();


        }
    }






    @FXML
    void accBT(ActionEvent event) throws IOException,SQLException, NoSuchAlgorithmException, ClassNotFoundException  {
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Confirm Acceptance");
        confirmationDialog.setHeaderText(null);
        confirmationDialog.setContentText("Are you sure you want to accept this request?");
        confirmationDialog.initOwner(acceptBT.getScene().getWindow());

        // Customize the buttons in the confirmation dialog
        ButtonType confirmButton = new ButtonType("Confirm", ButtonBar.ButtonData.YES);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.NO);
        confirmationDialog.getButtonTypes().setAll(confirmButton, cancelButton);

        // Show the confirmation dialog and wait for user input
        Optional<ButtonType> result = confirmationDialog.showAndWait();

        if (result.isPresent() && result.get() == confirmButton) {
            String driver = "com.mysql.cj.jdbc.Driver";
            String dbUrl = "jdbc:mysql://localhost:3306/uiubuddy";
            String userName = "root";
            String password = "";

            try (Connection conn = DriverManager.getConnection(dbUrl, userName, password)) {
                String sql = "UPDATE `members` SET status='Accepted' WHERE mid='" + smid + "'";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.executeUpdate();
                }

                showAlert(Alert.AlertType.CONFIRMATION, acceptBT.getScene().getWindow(), "Accept Request", "Request Accepted");
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, acceptBT.getScene().getWindow(), "Error", "An error occurred.");
            }
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
    void dltBT(ActionEvent event) throws IOException, SQLException, NoSuchAlgorithmException, ClassNotFoundException {
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Confirm Rejection");
        confirmationDialog.setHeaderText(null);
        confirmationDialog.setContentText("Are you sure you want to reject this request?");
        confirmationDialog.initOwner(deleteBT.getScene().getWindow());

        // Customize the buttons in the confirmation dialog
        ButtonType confirmButton = new ButtonType("Confirm", ButtonBar.ButtonData.YES);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.NO);
        confirmationDialog.getButtonTypes().setAll(confirmButton, cancelButton);

        // Show the confirmation dialog and wait for user input
        Optional<ButtonType> result = confirmationDialog.showAndWait();

        if (result.isPresent() && result.get() == confirmButton) {
            String driver = "com.mysql.cj.jdbc.Driver";
            String dbUrl = "jdbc:mysql://localhost:3306/uiubuddy";
            String userName = "root";
            String password = "";

            try (Connection conn = DriverManager.getConnection(dbUrl, userName, password)) {
                String sql = "DELETE FROM `members` WHERE mid='" + smid + "'";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.executeUpdate();
                }

                showAlert(Alert.AlertType.CONFIRMATION, deleteBT.getScene().getWindow(), "Reject Request", "Request rejected");
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, deleteBT.getScene().getWindow(), "Error", "An error occurred.");
            }
        }


    }
    @FXML
    void viewBT(ActionEvent event) throws IOException,NoSuchAlgorithmException,ClassNotFoundException,SQLException {
        String driver = "com.mysql.cj.jdbc.Driver";
        String databaseurl = "jdbc:mysql://localhost:3306/uiubuddy";
        String username = "root";
        String passwords = "";
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(databaseurl, username, passwords);

        Parent root = FXMLLoader.load(getClass().getResource("memView.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        Window owner = viewB.getScene().getWindow();

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




}

