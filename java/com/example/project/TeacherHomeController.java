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
import javafx.application.Platform;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.example.project.loginController.temail;


public class TeacherHomeController implements Initializable {
    @FXML
    private Button reject;
    public static String pid;
    @FXML
    private Button accept;
    @FXML
    private Button viewB;

    @FXML
    private Button cSchedule;

    @FXML
    private Button HomeB;

    @FXML
    private TableView<requestView> requests;

    @FXML
    private TableColumn<requestView, String> name;
    @FXML
    private TableColumn<requestView, String> id;
    @FXML
    private TableColumn<requestView, String> gid;

    @FXML
    private TableColumn<requestView, String> fydp;

    @FXML
    private TableColumn<requestView, String> day;
    ObservableList<requestView> list6 = FXCollections.observableArrayList();
    @FXML
    private ImageView a;
    @FXML
    private ImageView b;


    @FXML
    void homeBT(ActionEvent event) {

    }

    @FXML
    void createScBT(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CreateSchedule.fxml"));
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
        requests.setRowFactory(tv -> {
            return new TableRow<requestView>() {
                @Override
                protected void updateItem(requestView item, boolean empty) {
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
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM `counseling` WHERE memail='" + temail + "' AND status='Pending'");

            while (rs.next()) {
                list6.add(new requestView(rs.getString("name"), rs.getString("id"), rs.getString("gid"), rs.getString("fydp"), rs.getString("day")));
            }
            conn.close();
            rs.close();
        } catch (Exception e) {

        }
        name.setCellValueFactory(new PropertyValueFactory<requestView, String>("name"));
        id.setCellValueFactory(new PropertyValueFactory<requestView, String>("id"));
        gid.setCellValueFactory(new PropertyValueFactory<requestView, String>("gid"));
        fydp.setCellValueFactory(new PropertyValueFactory<requestView, String>("fydp"));
        day.setCellValueFactory(new PropertyValueFactory<requestView, String>("day"));


        requests.setItems(list6);
        requests.setOnMouseClicked(e -> {


                    events();
                }

        );

    }

    private void events() {
        for (requestView pick : requests.getSelectionModel().getSelectedItems()) {
//            for (int i = 1; i <= 1; i++)
            pid = pick.getId();


        }


    }
    @FXML
    void acceptBT(ActionEvent event) throws IOException, SQLException, NoSuchAlgorithmException, ClassNotFoundException {
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Confirm Acceptance");
        confirmationDialog.setHeaderText(null);
        confirmationDialog.setContentText("Are you sure you want to accept this request?");
        confirmationDialog.initOwner(accept.getScene().getWindow());

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
                String sql = "UPDATE `counseling` SET status='Accepted',`complete`='Running' WHERE id='" + pid + "'";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.executeUpdate();
                }

                showAlert(Alert.AlertType.CONFIRMATION, accept.getScene().getWindow(), "Accept Request", "Request Accepted");
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, accept.getScene().getWindow(), "Error", "An error occurred.");
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
    void rejectBT(ActionEvent event) throws IOException, SQLException, NoSuchAlgorithmException, ClassNotFoundException {
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Confirm Rejection");
        confirmationDialog.setHeaderText(null);
        confirmationDialog.setContentText("Are you sure you want to reject this request?");
        confirmationDialog.initOwner(reject.getScene().getWindow());

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
                String sql = "DELETE FROM `counseling` WHERE id='" + pid + "'";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.executeUpdate();
                }

                showAlert(Alert.AlertType.CONFIRMATION, reject.getScene().getWindow(), "Reject Request", "Request rejected");
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, reject.getScene().getWindow(), "Error", "An error occurred.");
            }
        }
    }

    @FXML
    void viewBT(ActionEvent event) throws IOException,NoSuchAlgorithmException,ClassNotFoundException,SQLException{
        String driver = "com.mysql.cj.jdbc.Driver";
        String databaseurl = "jdbc:mysql://localhost:3306/uiubuddy";
        String username = "root";
        String passwords = "";
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(databaseurl, username, passwords);

        Parent root = FXMLLoader.load(getClass().getResource("teacherViewStudent.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        Window owner = viewB.getScene().getWindow();

    }
    @FXML
    void runningBT(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("runningG.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void completeBT(ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("completeG.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private Button logout;
    @FXML
    void logoutBT(ActionEvent event) {
        Stage currentStage = (Stage) logout.getScene().getWindow();
        currentStage.close();
        Platform.exit();
    }


}
