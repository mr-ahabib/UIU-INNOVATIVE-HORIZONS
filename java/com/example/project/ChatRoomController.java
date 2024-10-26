package com.example.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static com.example.project.StloginController.*;
import static com.example.project.reqController.*;


public class ChatRoomController  extends Thread implements Initializable {
    Socket socket;
    String userName;
    BufferedReader reader;
    PrintWriter writer = null;
    @FXML
    private AnchorPane ChatAdda;

    @FXML
    private Pane chat;

    @FXML
    private Label clientName;

    @FXML
    private TextField msgField;

    @FXML
    private TextArea msgRoom;
    public void takename()
    {
        for (User user : users) {
            userName = user.name;
        }

    }

    @FXML
    void handleSendEvent(MouseEvent event) throws SQLException, ClassNotFoundException  {
        send();
        for (User user : users) {
            System.out.println(user.name);
        }

    }

    @FXML
    void homebt(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("profile.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }

    @FXML
    void sendMessageByKey(KeyEvent event) throws SQLException, ClassNotFoundException {
        if (event.getCode().toString().equals("ENTER")) {
            send();
        }

    }
    public void send() throws SQLException, ClassNotFoundException {
        takename();
        String msg = msgField.getText();

        String driver = "com.mysql.cj.jdbc.Driver";
        String databaseurl = "jdbc:mysql://localhost:3306/uiubuddy";
        String username = "root";
        String passwords = "";
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(databaseurl, username, passwords);
        String sql="INSERT INTO `chats`(`gid`, `msg`, `name`) VALUES (?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1,gid);
        ps.setString(2,msg);
        ps.setString(3,userName);

        ps.executeUpdate();

        conn.close();

        writer.println(userName  +" : " + msg);
        //writer.println(" ");
        msgRoom.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        //msgRoom.appendText("Me: " + msg + "\n");

        msgField.setText("");
        if (msg.equalsIgnoreCase("BYE") || (msg.equalsIgnoreCase("logout"))) {
            System.exit(0);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clientName.setText(name);


        String driver = "com.mysql.cj.jdbc.Driver";
        String dbUrl = "jdbc:mysql://localhost:3306/uiubuddy";
        String userName = "root";
        String password = "";








        takename();

        connectSocket();




        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(dbUrl, userName, password);
            String sql = "SELECT * FROM `chats` WHERE gid='" + gid + "' ";
            PreparedStatement pst = conn.prepareStatement(sql);


            ResultSet rsz = pst.executeQuery();


            while (rsz.next()) {
                //        list2.add(new studenthomeshow(rsz.getString("tname"), rsz.getString("stime"),rsz.getString("endtime"), rsz.getString("status"), rsz.getString("date")));
                msgRoom.appendText(rsz.getString(3)+" :- "+rsz.getString(2)+"\n");
            }

            conn.close();
            rsz.close();
        } catch (Exception e) {
        }
    }

    private void connectSocket() {
        try {
            socket = new Socket("127.0.0.1", 5555);
            System.out.println("Socket is connected with server!");
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
            this.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public boolean saveControl = false;
    @Override
    public void run() {
        try {
            while (true) {
                String msg = reader.readLine();
                String[] tokens = msg.split(" ");
                String cmd = tokens[0];
                System.out.println(cmd);
                StringBuilder fulmsg = new StringBuilder();
                for(int i = 1; i < tokens.length; i++) {
                    fulmsg.append(tokens[i]);
                }
                System.out.println(fulmsg);
                if (cmd.equalsIgnoreCase(userName + ":")) {
                    continue;
                } else if(fulmsg.toString().equalsIgnoreCase("bye")) {
                    break;
                }
                msgRoom.appendText(msg + "\n");
            }
            reader.close();
            writer.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
