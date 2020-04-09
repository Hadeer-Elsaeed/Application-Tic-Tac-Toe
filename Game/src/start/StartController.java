
package start;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import scene.ChangeView;


public class StartController implements Initializable {
    
    ChangeView cv = new ChangeView();
    public static int mode;
    public void start(ActionEvent event) throws IOException{
        mode = 1;
        cv.changeScene("/XO/XO.fxml", event);
    }
    public void Two(ActionEvent event) throws IOException{
        mode = 2;
        cv.changeScene("/TwoPlayers/Two.fxml", event);
    }
    public void Three(ActionEvent event) throws IOException{
        mode = 3;
        cv.changeScene("/loginscene/Login.fxml", event);
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //
    }    
    
   
}
