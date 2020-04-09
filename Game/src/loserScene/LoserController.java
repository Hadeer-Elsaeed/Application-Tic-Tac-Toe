/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loserScene;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import scene.ChangeView;
import start.StartController;

/**
 * FXML Controller class
 *
 * @author mahmo
 */
public class LoserController implements Initializable {

    ChangeView cv = new ChangeView();

    @FXML
    private MediaView mv;
    private MediaPlayer mp;
    private Media me;
    Socket mySocket;
    DataInputStream dis;
    DataOutputStream dos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        String path = new File("src/media/loser.mp4").getAbsolutePath();
        me = new Media(new File(path).toURI().toString());
        mp = new MediaPlayer(me);
        mv.setMediaPlayer(mp);
        mp.setAutoPlay(true);
        mySocket = start.TicTacToeV2.mySocket;
        dis = start.TicTacToeV2.dis;
        dos = start.TicTacToeV2.dos;

    }

    public void changeScreenToRematch(ActionEvent event) throws IOException {
        mp.stop();
        if (StartController.mode == 1 || StartController.mode == 2) {
            cv.changeScene("/rematchScene/rematchView.fxml", event);
        } else {
            cv.changeScene("/onlinePlayers/onlineXML.fxml", event);
            dos.writeUTF("reload:" + makeReq.ReqController.p1name);

        }
    }
}
