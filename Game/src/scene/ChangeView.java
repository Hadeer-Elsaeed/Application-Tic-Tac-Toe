/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scene;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import onlineBoards.TwoController;

/**
 *
 * @author mahmo
 */
public class ChangeView {

    public void changeScene(String path, ActionEvent event) throws IOException {
        Parent gameParent = FXMLLoader.load(getClass().getResource(path));
        Scene gameScene = new Scene(gameParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(gameScene);
        window.show();
    }

    public void changeSceneMouse(String path, MouseEvent event) throws IOException {
        Parent gameParent = FXMLLoader.load(getClass().getResource(path));
        Scene gameScene = new Scene(gameParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(gameScene);
        window.show();
    }

    public void changeScene2(String path, ActionEvent event) throws IOException {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Parent gameParent = FXMLLoader.load(getClass().getResource(path));
                    Scene gameScene = new Scene(gameParent);
                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    window.setScene(gameScene);
                    window.show();
                } catch (IOException ex) {
                    Logger.getLogger(TwoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

}
