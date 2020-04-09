/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drawScene;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import scene.ChangeView;
import start.StartController;

/**
 * FXML Controller class
 *
 * @author mahmo
 */
public class DrawController implements Initializable {

    ChangeView cv = new ChangeView();
    Socket mySocket;
    DataInputStream dis;
    DataOutputStream dos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        mySocket = start.TicTacToeV2.mySocket;
        dis = start.TicTacToeV2.dis;
        dos = start.TicTacToeV2.dos;
    }

    public void changeScreenToRematch(ActionEvent event) throws IOException {
        if (StartController.mode == 1 || StartController.mode == 2) {
            cv.changeScene("/rematchScene/rematchView.fxml", event);
        } else {
            cv.changeScene("/onlinePlayers/onlineXML.fxml", event);
            dos.writeUTF("reload:" + makeReq.ReqController.p1name);

        }
    }
}
