package loginscene;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import makeReq.ReqController;
import scene.ChangeView;

public class LoginController extends Thread implements Initializable {

    private String name;
    ChangeView cv = new ChangeView();
    public static ActionEvent threadEvent;
    private boolean isRunning = false;
    
    //connection variables
    Socket mySocket;
    DataInputStream dis;
    DataOutputStream dos;

    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;
    @FXML
    private Label checkLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            mySocket = start.TicTacToeV2.mySocket;
            dis = start.TicTacToeV2.dis;
            dos = start.TicTacToeV2.dos;
            if (mySocket != null) {
                System.out.println("login Intialized");
            }
            isRunning = true;
            start();
        } catch (Exception ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //signup Buton //
    @FXML
    public void moveToRegisterationScene(ActionEvent event) throws IOException {
        //dis.close();
        isRunning = false;
        cv.changeScene("/signup/registration.fxml", event);
    }

    //Login Button//
    @FXML
    public void moveToOnlineGameScene(ActionEvent event) throws IOException { //edit the method & scenes name and path// 
        //send login data to server
        threadEvent = event;
        name = usernameField.getText();
        String password = passwordField.getText();
        String msg = "login:" + name + ":" + password;
        dos.writeUTF(msg);

    }

    //Back To main menu//
    @FXML
    public void moveToMainScene(ActionEvent event) throws IOException {
        isRunning = false;
        cv.changeScene("/start/start.fxml", event);
    }

    @Override
    public void run() {
        while (isRunning) {
            try {
                if (dis.available() <= 0) {
                    continue;
                }

                String reply = dis.readUTF();
                String[] str = reply.split("\\:");
                System.out.println(str[0]);
                //System.out.println(str[1]);
                if (str[0].equals("loggedin")) {
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
                    } else {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    ReqController.p1name = name;
                                    checkLabel.setText("*Wrong username or password");
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        });
                        
                        System.out.println("username or password is not right");
                    }
                }
                sleep(200);
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

}
