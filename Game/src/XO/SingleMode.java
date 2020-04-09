/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XO;

import java.util.Random;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

/**
 *
 * @author user
 */
public class SingleMode {

    @FXML private Button btn1;
    @FXML private Button btn2;
    @FXML private Button btn3;
    @FXML private Button btn4;
    @FXML private Button btn5;
    @FXML private Button btn6;
    @FXML private Button btn7;
    @FXML private Button btn8;
    @FXML private Button btn9;

   
    int[] arr = new int[9];
    int drawCounter = 0;

    public void computerPlay() {
        Random rand;
        int pos;
        // create instance of Random class 
        rand = new Random();
        
        // Generate random integers in range 0 to 2
        do {
            pos =0;
            pos = rand.nextInt(8);
            System.out.println(arr[pos]);
            System.out.println(pos);
        } while (arr[pos] != 0);

        switch (pos) {
            case 0:
                btn1.setText("o");
                btn1.setTextFill(Color.ORANGE);
                arr[pos] = -1 ;
                drawCounter++;
                break;
            case 1:
                btn2.setText("o");
                btn2.setTextFill(Color.ORANGE);
                arr[pos] = -1;
                drawCounter++;
                break;
            case 2:
                btn3.setText("o");
                btn3.setTextFill(Color.ORANGE);
                arr[pos] = -1;
                drawCounter++;
                break;
            case 3:
                btn4.setText("o");
                btn4.setTextFill(Color.ORANGE);
                arr[pos] = -1;
                drawCounter++;
                break;
            case 4:
                btn5.setText("o");
                btn5.setTextFill(Color.ORANGE);
                arr[pos] = -1;
                drawCounter++;
                break;
            case 5:
                btn6.setText("o");
                btn6.setTextFill(Color.ORANGE);
                arr[pos] = -1;
                drawCounter++;
                break;
            case 6:
                btn7.setText("o");
                btn7.setTextFill(Color.ORANGE);
                arr[pos] = -1;
                drawCounter++;
                break;
            case 7:
                btn8.setText("o");
                btn8.setTextFill(Color.ORANGE);
                arr[pos] = -1;
                drawCounter++;
                break;
            case 8:
                btn9.setText("o");
                btn9.setTextFill(Color.ORANGE);
                arr[pos] = -1;
                drawCounter++;
                break;
            default:
                break;
               
                
        }
    }
        public int checkForWinner(){
        
        //check for rows
       
            if(arr[0]== arr[1] && arr[1] == arr[2])
            {
                return arr[0];
            }
             if(arr[3]== arr[4] && arr[4] == arr[5])
            {
                return arr[3];
            }
             if(arr[6]== arr[7] && arr[6] == arr[8])
            {
                return arr[6];
            }
        //check for columns
         if(arr[0]== arr[3] && arr[3] == arr[6])
            {
                return arr[0];
            }
          if(arr[1]== arr[4] && arr[4] == arr[7])
            {
                return arr[1];
            }
           if(arr[2]== arr[5] && arr[5] == arr[8])
            {
                return arr[2];
            }
        //check for diagonals
       if(arr[0]== arr[4] && arr[4] == arr[8] )
            {
                return arr[0];
            }
        if(arr[2]== arr[4] && arr[4] == arr[6])
            {
                return arr[2];
            }
        if(drawCounter == 9 )
            return 2;
        
     
        return 0;
    }
}


