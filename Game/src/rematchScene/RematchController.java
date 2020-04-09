/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rematchScene;

import java.io.IOException;
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
public class RematchController implements Initializable {

    ChangeView cv = new ChangeView();
    
    
    public void changeScrenToPlayAgain(ActionEvent event) throws IOException{
        if(StartController.mode==1){
            
            cv.changeScene("/XO/XO.fxml", event);
        }
        else if(StartController.mode==2)
        {
             cv.changeScene("/TwoPlayers/Two.fxml", event);
        }
        else{
            cv.changeScene("/onlineBoards/Two.fxml", event);
        }
    }
    
    public void changeScreenToMain(ActionEvent event) throws IOException{
        TwoPlayers.TwoController.p1score =0;
        TwoPlayers.TwoController.p2score =0;
        XO.XOController.p1score = 0;
        XO.XOController.p2score = 0;
        cv.changeScene("/start/start.fxml", event);
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
