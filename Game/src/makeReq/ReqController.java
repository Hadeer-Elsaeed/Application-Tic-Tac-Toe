/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package makeReq;

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
import loginscene.LoginController;
import scene.ChangeView;

/**
 * FXML Controller class
 *
 * @author Ahmed
 */
public class ReqController extends Thread implements Initializable {
    
    //connection variables
    Socket mySocket;
    DataInputStream dis ;
    DataOutputStream dos ;
    
    ChangeView cv = new ChangeView();
    
    ActionEvent threadEvent;
    private boolean isRunning;
    public static String p1name;
    public static String p2name;
    public static String play = "x";
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            mySocket = start.TicTacToeV2.mySocket;
            dis = start.TicTacToeV2.dis;
            dos = start.TicTacToeV2.dos;
            if (mySocket != null) {
                System.out.println("Request Listner is running");
            }
            isRunning = true;
            start();
        } catch (Exception ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        onlineBoards.TwoController.myTurn = true;   
    }    

    @FXML
    private void backToOnlinePlayers(ActionEvent event) throws IOException {
        dos.writeUTF("cancelReq:"+p2name);
        cv.changeScene("/onlinePlayers/onlineXML.fxml", event);
        isRunning = false;
    }
    
    @FXML
    private void sendReq(ActionEvent event) throws IOException {
        threadEvent = event;
        String str = "playReq:"+p1name+":"+p2name+":"+play;
        dos.writeUTF(str);
    }

    @FXML
    private void setX(ActionEvent event) {
        play="x";
        onlineBoards.TwoController.myTurn = true;
        System.out.println(""+play);
    }

    @FXML
    private void setO(ActionEvent event) {
        onlineBoards.TwoController.myTurn = false;
        play="o";
        System.out.println(""+play);
    }

    @FXML
    private void recordGame(ActionEvent event){
            System.out.println("Recorded");
            isRunning = false;
    }
    
    @Override
    public void run(){
        while(isRunning){
            try {
                if (dis.available() <= 0) {
                    continue;
                }
                String msg = dis.readUTF();
                System.out.println(msg);
                String[] str = msg.split("\\:");
//                System.out.println(str[0]);
//                System.out.println(str[1]);
                if (str[0].equals("acceptance")) {
                    if (str[1].equals("yes")) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    cv.changeScene("/onlineBoards/Two.fxml", threadEvent);
                                    isRunning = false;
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        });
                    }
                    else{
                    Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    //dis.close();
                                    cv.changeScene("/onlinePlayers/onlineXML.fxml", threadEvent);
                                    isRunning = false;
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        });
                    }
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
