/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlinePlayers;

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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import loginscene.LoginController;
import makeReq.ReqController;
import scene.ChangeView;

/**
 * FXML Controller class
 *
 * @author Ahmed
 */
public class OnlineXMLController extends Thread implements Initializable {

    @FXML
    private ListView<String> lstView;
    ObservableList<String> list = FXCollections.observableArrayList();
    @FXML
    private Button fireEvent;

    ChangeView cv = new ChangeView();
    ActionEvent threadEvent;
    private boolean isRunning;

    //connection variables
    Socket mySocket;
    DataInputStream dis;
    DataOutputStream dos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lstView.setItems(list);
        try {
            mySocket = start.TicTacToeV2.mySocket;
            dis = start.TicTacToeV2.dis;
            dos = start.TicTacToeV2.dos;
            if (mySocket != null) {
                System.out.println("OnlinePlayer Listner is running");
            }
            isRunning = true;
            start();
            System.out.println("OnlinePlayer Listner is running");
            dos.writeUTF("pNamesAgain:" + ReqController.p1name);
        } catch (Exception ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void backToMenu(ActionEvent event) throws IOException {
        cv.changeScene("/start/start.fxml", event);
        isRunning = false;
    }

    @FXML
    private void gotoPreGames(ActionEvent event) throws IOException {
        cv.changeScene("/PreGames/PreGames.fxml", event);
        isRunning = false;
    }

    @FXML
    private void makeReq(MouseEvent event) throws IOException {
        ReqController.p2name = lstView.getSelectionModel().getSelectedItem();
        if (ReqController.p2name != null) {
            cv.changeSceneMouse("/makeReq/Req.fxml", event);
            isRunning = false;
        }
    }

    @FXML
    private void fireEventbtn(ActionEvent event) throws IOException {
        cv.changeScene("/receiveReq/ReceiveReq.fxml", event);
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
                if (str[0].equals("request")) {
                    ReqController.p2name = str[1];
                    ReqController.play = str[2];
                    if (str[2].equals("x")) {
                        onlineBoards.TwoController.myTurn = false;
                    } else if (str[2].equals("o")) {
                        onlineBoards.TwoController.myTurn = true;
                    }
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            fireEvent.fire();
                            isRunning = false;
                        }
                    });

                } else if (str[0].equals("pname")) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            if (!str[1].equals(makeReq.ReqController.p2name)) {
                                list.add(str[1]);
                            }
                        }
                    });

                } else if (str[0].equals("pnames")) {
                    System.out.println(str[0]);
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            if (str.length > 1) {
                                for (int i = 1; i < str.length; i++) {
                                    list.add(str[i]);
                                }
                            }
                        }
                    });

                } else if (str[0].equals("remove")) {
                    System.out.println(str[0]);
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {

                            System.out.println("xmlCon. item1 remove:" + list.removeAll(str[1]));
                            System.out.println("xmlCon. item2 remove:" + list.removeAll(str[1]));

                            list.removeAll(str[1]);
                            list.removeAll(str[2]);
                            lstView.refresh();

                        }
                    });

                }

            } catch (IOException ex) {
                Logger.getLogger(ReqController.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(ReqController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
