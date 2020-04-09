///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package loginscene;
//
//import java.io.DataInputStream;
//import java.io.DataOutputStream;
//import java.io.IOException;
//import java.net.Socket;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javafx.application.Platform;
//
///**
// *
// * @author mahmo
// */
//
//public class Listner extends Thread{
//    
//    Socket mySocket;
//    DataInputStream dis;
//    DataOutputStream dos;
//    public Listner(){
//        mySocket = start.TicTacToeV2.mySocket;
//        dis = start.TicTacToeV2.dis;
//        dos = start.TicTacToeV2.dos;
//    }
//    
//        
//    public void run(){
//        while(true){
//            try {
//                // get output of authenticaion from server 
//                String msg = dis.readUTF();
//                String[] str = msg.split("\\:");
//                System.out.println(str[0]);
//                //System.out.println(str[1]);
//                if(str[0].equals("loggedin")){
//                    if(str[1].equals("yes")){
//                        Platform.runLater(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                makeReq.ReqController.p1name = name;
//                                cv.changeScene("/onlinePlayers/onlineXML.fxml", eventOnline);
//                                stop();
//                            } catch (IOException ex) {
//                                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
//                            }
//                        }
//                        });
//
//                    }
//                    else{
//                        Platform.runLater(new Runnable() {
//                        @Override
//                        public void run() {
//                           checkLabel.setText("*Wrong username or password");
//
//                        }
//                        });
//                        //usernameField.setPromptText("username or password is not right");
//                        System.out.println("username or password is not right");
//                    } 
//                }
//
//            } catch (IOException ex) {
//                try {
//                    System.out.println("Server is down");
//                    dos.close();
//                    dis.close();
//                    mySocket.close();
//                    stop();
//                } catch (IOException ex1) {
//                    ex1.printStackTrace();
//                }
//            }
//        }
//
//    }
//}
