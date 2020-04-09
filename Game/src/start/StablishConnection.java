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

/**
 *
 * @author mahmo
 */
public class StablishConnection {
    Socket mySocket;
    DataInputStream dis ;
    DataOutputStream dos ;
    public void connect(){
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

    }

    public Socket getMySocket() {
        return mySocket;
    }

    public DataInputStream getDis() {
        return dis;
    }

    public DataOutputStream getDos() {
        return dos;
    }

    
    
}
