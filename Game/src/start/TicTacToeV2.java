/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package start;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author mahmo
 */
public class TicTacToeV2 extends Application {
    
    public static Socket mySocket;
    public static DataInputStream dis ;
    public static DataOutputStream dos ;
    
    @Override
    public void start(Stage primaryStage) throws IOException {
       
        
        Parent root = FXMLLoader.load(getClass().getResource("start.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            //Player intialization
            mySocket = new Socket("127.0.0.1",5005);
            if(mySocket!=null){
                dis = new DataInputStream(mySocket.getInputStream());
                dos = new DataOutputStream(mySocket.getOutputStream());
                System.out.println("player is connected \n");
            }
            } catch (IOException ex) {
                System.out.println("Unable to connect to server plz wait");
        }
        
        launch(args);
    }
    
}
