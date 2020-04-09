/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TwoPlayers;

import java.io.IOException;
import javafx.event.ActionEvent;
import scene.ChangeView;
/**
 *
 * @author mahmo
 */
public class XOAlgo {
    
    ChangeView cv = new ChangeView();
    
    private int[][] arr = new int[3][3];
    private int counter;
    private int winner;
    
    public XOAlgo(){
        counter = 0;
        winner = 0;
    }
    //set the array Value
    public void setArr(int a , int b , int c){
        arr[a][b] = c;
        counter++;
    }
    
    //ser arr to zero
    public void zerosArr(){
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                arr[row][col] = 0;
            }
        }
    }
    
   //get counter
    public int getCounter(){
        return counter;
    }
    //get array
    public int[][] getArray(){
        return arr;
    }
    public int getValue(int a , int b)
    {
        return arr[a][b];
    }
    //check for winner
    public int checkForWinner(){
        
        //check for rows
        for(int i=0 ; i<3 ; i++){
            if(arr[i][0]==arr[i][1] && arr[i][1]==arr[i][2] && arr[i][0]!=0)
                return arr[i][0];
        }
        //check for columns
        for(int i=0 ; i<3 ; i++){
            if(arr[0][i]==arr[1][i] && arr[1][i]==arr[2][i] && arr[0][i]!=0)
                return arr[0][i];
        }
        //check for diagnols
        if(arr[0][0]==arr[1][1] && arr[1][1]==arr[2][2] && arr[0][0]!=0)
            return arr[0][0];
        if(arr[0][2]==arr[1][1] && arr[1][1]==arr[2][0] && arr[1][1]!=0)
            return arr[1][1];
        if(counter==9)
            return 2;
        return 0;
    }
    
    public void resultScene(ActionEvent event) throws IOException{
        winner = checkForWinner();
        switch(winner){
            case 1 :
                //obj.zerosArr();
                ++TwoController.p1score;
                cv.changeScene("/winnerScene/winnerView.fxml", event);
                break;
            case -1 :
                //obj.zerosArr();
                 ++TwoController.p2score;
                cv.changeScene("/loserScene/loserView.fxml", event);
                break;
            case 2 :
                //obj.zerosArr();
                cv.changeScene("/drawScene/drawView.fxml", event);
                break;
        }
    }
}
