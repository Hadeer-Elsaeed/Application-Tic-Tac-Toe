/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signup;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import loginscene.LoginController;
import makeReq.ReqController;
import scene.ChangeView;

public class RegistrationController extends Thread implements Initializable {

    ChangeView cv = new ChangeView();
    String name;
    //connection variables
    Socket mySocket;
    DataInputStream dis;
    DataOutputStream dos;

    ActionEvent threadEvent;
    boolean isRunning;

    private static Pattern userNamePattern = Pattern.compile("^[a-zA-Z0-9]{4,12}$");
    private static Pattern passPattern = Pattern.compile("^[0-9a-z]{4,12}$");
    Matcher mtchName;
    Matcher mtchPass;

    @FXML
    private TextField userNameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField repasswordField;
    @FXML
    private Label checkLabel;
    @FXML
    private Label checkPasswordLabel;
    @FXML
    private TextField usernameField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            mySocket = start.TicTacToeV2.mySocket;
            dis = start.TicTacToeV2.dis;
            dos = start.TicTacToeV2.dos;
            if (mySocket != null) {
                System.out.println("Registration Listner is running");
            }
            isRunning = true;
            start();
        } catch (Exception ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //check repassword== password& name != null//
    public void checkNameAndPassword(ActionEvent event) throws IOException {

        threadEvent = event;
        
        mtchName = userNamePattern.matcher(userNameField.getText());
        mtchPass = passPattern.matcher(passwordField.getText());
        //send login data to server
        if (mtchName.matches()) {
            if (repasswordField.getText().compareTo(passwordField.getText()) == 0 && mtchPass.matches()) {
                {

                    name = userNameField.getText();
                    String password = passwordField.getText();
                    String data = "signup:" + name + ":" + password;
                    dos.writeUTF(data);
                }

            } else if (repasswordField.getText().compareTo(passwordField.getText()) == 1) {
                checkPasswordLabel.setText("*Please re-type the password ");

            } else {
                checkPasswordLabel.setText("password must be at least 4 digits / charcters only ");

            }
        } 
        else {
            checkPasswordLabel.setText("user name must be at least 4 digits / charcters only");

        }
    }

    //cancel Button//
    @FXML
    public void moveToLoginScene(ActionEvent event) throws IOException {
        cv.changeScene("/loginscene/Login.fxml", event);
        isRunning = false;
    }

    @Override
    public void run() {
        while (isRunning) {
            try {
                if (dis.available() <= 0) {
                    continue;
                }

                String msg = dis.readUTF();
                System.out.println(msg);
                String[] str = msg.split("\\:");
                System.out.println(str[0]);
                System.out.println(str[1]);
                if (str[0].equals("signedup")) {
                    if (str[1].equals("yes")) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    ReqController.p1name = name;
                                    cv.changeScene("/onlinePlayers/onlineXML.fxml", threadEvent);
                                    isRunning = false;
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        });
                    }

                } else {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            checkLabel.setText("*Wrong username or password");

                        }
                    });
                    usernameField.setPromptText("username or password is not right");
                    System.out.println("username or password is not right");
                }

            } catch (IOException ex) {
                try {
                    System.out.println("Server is down");
                    // Todo:: Offline mode - return to main scene.
                } catch (Exception ex1) {
                    ex1.printStackTrace();
                }
            }
            try {
                sleep(200);
            } catch (InterruptedException ex) {
                Logger.getLogger(RegistrationController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
