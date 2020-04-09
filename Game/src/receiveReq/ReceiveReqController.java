/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package receiveReq;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import loginscene.LoginController;
import makeReq.ReqController;
import scene.ChangeView;
import start.StablishConnection;

/**
 * FXML Controller class
 *
 * @author Ahmed
 */
public class ReceiveReqController extends Thread implements Initializable {

    @FXML
    private Text pname;
    ChangeView cv = new ChangeView();
    @FXML
    Button btnRefused;

    //connection variables
    StablishConnection con;
    Socket mySocket;
    DataInputStream dis;
    DataOutputStream dos;

    private boolean isRunning;
    private boolean isFired;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pname.setText(ReqController.p2name + " wants to play");
        try {
            mySocket = start.TicTacToeV2.mySocket;
            dis = start.TicTacToeV2.dis;
            dos = start.TicTacToeV2.dos;
            if (mySocket != null) {
                System.out.println("received request Intialized");
            }
            isRunning = true;
            start();
        } catch (Exception ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void openXOBoard(ActionEvent event) throws IOException {
        dos.writeUTF("acceptedReq:" + ReqController.p2name + ":" + "yes" + ":" + ReqController.p1name);
        cv.changeScene("/onlineBoards/Two.fxml", event);
        isRunning = false;
    }

    @FXML
    private void goToOnlinePlayers(ActionEvent event) throws IOException {
        if (!isFired) {
            dos.writeUTF("acceptedReq:" + ReqController.p2name + ":" + "no");
        }
        cv.changeScene("/onlinePlayers/onlineXML.fxml", event);
        isRunning = false;
    }

    @FXML
    private void recordGameReceived(ActionEvent event) {
        System.out.println("Recorded");
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
                if (msg.equals("reqCanceled")) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            isFired = true;
                            btnRefused.fire();
                        }
                    });
                }

            } catch (IOException ex) {
                Logger.getLogger(ReqController.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                sleep(200);
            } catch (InterruptedException ex) {
                Logger.getLogger(ReqController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
