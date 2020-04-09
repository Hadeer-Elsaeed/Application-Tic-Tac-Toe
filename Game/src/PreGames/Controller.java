/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PreGames;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ahmed
 */
public class Controller implements Initializable {

    @FXML
    private Button btnBk;
    @FXML
    private ListView<String> lstView;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lstView.getItems().addAll("Game 1", "Game 2", "Game 3");
    }    

    @FXML
    private void bkToOnline(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/onlinePlayers/onlineXML.fxml"));
        Scene gameScene = new Scene(root);        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();        
        window.setScene(gameScene);
        window.show();
    }
    
}
