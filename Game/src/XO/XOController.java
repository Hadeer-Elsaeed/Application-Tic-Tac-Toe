/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XO;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import scene.ChangeView;

/**
 *
 * @author user
 */
public class XOController extends SingleMode implements Initializable{
    
    ChangeView cv = new ChangeView();

    @FXML private Button btn1;
    @FXML private Button btn2;
    @FXML private Button btn3;
    @FXML private Button btn4;
    @FXML private Button btn5;
    @FXML private Button btn6;
    @FXML private Button btn7;
    @FXML private Button btn8;
    @FXML private Button btn9;
    @FXML private Label p1ScoreLabel;
    @FXML private Label pcScoreLab;

    private int winner;
    public static int p1score ;
    public static int p2score ;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        p1ScoreLabel.setText(String.valueOf(p1score));
        pcScoreLab.setText(String.valueOf(p2score));
    }  
    
    public void changeScreenToMain(ActionEvent event) throws IOException {
        TwoPlayers.TwoController.p1score =0;
        TwoPlayers.TwoController.p2score =0;
        XO.XOController.p1score = 0;
        XO.XOController.p2score = 0;
        cv.changeScene("/start/start.fxml", event);
    }


    public void btnMethod(Button btn, int x) {
        if (arr[x] == 0) {
            btn.setText("x");
            btn.setTextFill(Color.BLUE);
            arr[x] = 1;
            drawCounter++;
            if (drawCounter != 9) {
                computerPlay();
            }
            winner = checkForWinner();

        }
    }
    
    public void changeScene2(ActionEvent event) throws IOException{
        
        if (winner != 0) {
                if (winner == 2) {
                    cv.changeScene("/drawScene/drawView.fxml", event);
                }
                else if(winner == 1){
                    p1score++;
                    cv.changeScene("/winnerScene/winnerView.fxml", event);
                }
                else if(winner == -1){
                    p2score++;
                    cv.changeScene("/loserScene/loserView.fxml", event);
                }
            }
        
    }

    public void btn1Handler(ActionEvent event) throws IOException {
        btnMethod(btn1, 0);
        changeScene2(event);
    }

    public void btn2Handler(ActionEvent event) throws IOException {
        btnMethod(btn2, 1);
        changeScene2(event);
    }

    public void btn3Handler(ActionEvent event) throws IOException {
        btnMethod(btn3, 2);
        changeScene2(event);
    }

    public void btn4Handler(ActionEvent event) throws IOException {
        btnMethod(btn4, 3);
        changeScene2(event);
    }

    public void btn5Handler(ActionEvent event) throws IOException {
        btnMethod(btn5, 4);
        changeScene2(event);
    }

    public void btn6Handler(ActionEvent event) throws IOException {
        btnMethod(btn6, 5);
        changeScene2(event);
    }

    public void btn7Handler(ActionEvent event) throws IOException {
        btnMethod(btn7, 6);
        changeScene2(event);
    }

    public void btn8Handler(ActionEvent event) throws IOException {
        btnMethod(btn8, 7);
        changeScene2(event);
    }

    public void btn9Handler(ActionEvent event) throws IOException {
        btnMethod(btn9, 8);
        changeScene2(event);
    }
}

