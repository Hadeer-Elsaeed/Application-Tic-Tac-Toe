package onlineBoards;

import static TwoPlayers.TwoController.p1score;
import static TwoPlayers.TwoController.p2score;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import scene.ChangeView;
import makeReq.ReqController;

/**
 * FXML Controller class
 *
 * @author Hadeer
 */
public class TwoController extends Thread implements Initializable {

    Socket soc;
    DataInputStream dis;
    DataOutputStream dos;
    PrintStream ps;
    private boolean isRunning = false;

    public static boolean myTurn = false;

    String player2;
    boolean flag = false;
    XOAlgo obj = new XOAlgo();
    ChangeView cv = new ChangeView();

    ActionEvent threadEvent;

    @FXML
    Button btn1;
    @FXML
    Button btn2;
    @FXML
    Button btn3;
    @FXML
    Button btn4;
    @FXML
    Button btn5;
    @FXML
    Button btn6;
    @FXML
    Button btn7;
    @FXML
    Button btn8;
    @FXML
    Button btn9;

    @FXML
    Label score1;
    @FXML
    Label score2;

    int winner2 = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        score1.setText(String.valueOf(p1score));
        score2.setText(String.valueOf(p2score));

        soc = start.TicTacToeV2.mySocket;
        dis = start.TicTacToeV2.dis;
        dos = start.TicTacToeV2.dos;
        player2 = ReqController.p2name;
        System.out.println("p1name :" + ReqController.p1name);
        System.out.println("p2name :" + ReqController.p2name);
        isRunning = true;
        start();
        System.out.println("online listner is running");

        threadEvent = loginscene.LoginController.threadEvent;
    }

    public void changeScreenToMain(ActionEvent event) throws IOException {
        cv.changeScene("/start/start.fxml", event);
    }

    //btn1 Action handler
    public void btn1Handler(ActionEvent event) throws IOException {
        if (myTurn && btn1.getText().isEmpty()) {
            dos.writeUTF("move:1:" + player2);
            System.out.println("move:1:" + player2);
            btnChanger(btn1, event, 0, 0);
            myTurn = !myTurn;
        }
        threadEvent = event;

    }

    public void btn2Handler(ActionEvent event) throws IOException {
        if (myTurn && btn2.getText().isEmpty()) {
            dos.writeUTF("move:2:" + player2);
            btnChanger(btn2, event, 0, 1);
            myTurn = !myTurn;
        }
        threadEvent = event;
    }

    public void btn3Handler(ActionEvent event) throws IOException {
        if (myTurn && btn3.getText().isEmpty()) {
            dos.writeUTF("move:3:" + player2);
            btnChanger(btn3, event, 0, 2);
            myTurn = !myTurn;
        }
        threadEvent = event;
    }

    public void btn4Handler(ActionEvent event) throws IOException {
        if (myTurn && btn4.getText().isEmpty()) {
            dos.writeUTF("move:4:" + player2);
            btnChanger(btn4, event, 1, 0);
            myTurn = !myTurn;
        }
        threadEvent = event;
    }

    public void btn5Handler(ActionEvent event) throws IOException {
        if (myTurn&& btn5.getText().isEmpty()) {
            dos.writeUTF("move:5:" + player2);
            btnChanger(btn5, event, 1, 1);
            myTurn = !myTurn;
        }
        threadEvent = event;
    }

    public void btn6Handler(ActionEvent event) throws IOException {
        if (myTurn&& btn6.getText().isEmpty()) {
            dos.writeUTF("move:6:" + player2);
            btnChanger(btn6, event, 1, 2);
            myTurn = !myTurn;
        }
        threadEvent = event;
    }

    public void btn7Handler(ActionEvent event) throws IOException {
        if (myTurn&& btn7.getText().isEmpty()) {
            dos.writeUTF("move:7:" + player2);
            btnChanger(btn7, event, 2, 0);
            myTurn = !myTurn;
        }
        threadEvent = event;
    }

    public void btn8Handler(ActionEvent event) throws IOException {
        if (myTurn&& btn8.getText().isEmpty()) {
            dos.writeUTF("move:8:" + player2);
            btnChanger(btn8, event, 2, 1);
            myTurn = !myTurn;

        }
        threadEvent = event;
    }

    public void btn9Handler(ActionEvent event) throws IOException {
        if (myTurn&& btn9.getText().isEmpty()) {
            dos.writeUTF("move:9:" + player2);
            btnChanger(btn9, event, 2, 2);
            myTurn = !myTurn;
        }
        threadEvent = event;
    }

    public void btnChanger(Button btn, ActionEvent event, int a, int b) throws IOException {
        if (obj.getValue(a, b) == 0) {

            if (obj.getCounter() % 2 != 0) {
                btn.setTextFill(Color.ORANGE);
                btn.setText("O");
                obj.setArr(a, b, -1);
            } else {
                btn.setTextFill(Color.BLUE);
                btn.setText("X");
                obj.setArr(a, b, 1);
            }
            obj.resultScene(event);
        }
    }

    class XOAlgo {

        ChangeView cv = new ChangeView();

        private int[][] arr = new int[3][3];
        private int counter;
        private int winner;

        public XOAlgo() {
            counter = 0;
            winner = 0;
        }
        //set the array Value

        public void setArr(int a, int b, int c) {
            arr[a][b] = c;
            counter++;
        }

        //ser arr to zero
        public void zerosArr() {
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    arr[row][col] = 0;
                }
            }
        }

        //get counter
        public int getCounter() {
            return counter;
        }
        //get array

        public int[][] getArray() {
            return arr;
        }

        public int getValue(int a, int b) {
            return arr[a][b];
        }
        //check for winner

        public int checkForWinner() {

            //check for rows
            for (int i = 0; i < 3; i++) {
                if (arr[i][0] == arr[i][1] && arr[i][1] == arr[i][2] && arr[i][0] != 0) {
                    return arr[i][0];
                }
            }
            //check for columns
            for (int i = 0; i < 3; i++) {
                if (arr[0][i] == arr[1][i] && arr[1][i] == arr[2][i] && arr[0][i] != 0) {
                    return arr[0][i];
                }
            }
            //check for diagnols
            if (arr[0][0] == arr[1][1] && arr[1][1] == arr[2][2] && arr[0][0] != 0) {
                return arr[0][0];
            }
            if (arr[0][2] == arr[1][1] && arr[1][1] == arr[2][0] && arr[1][1] != 0) {
                return arr[1][1];
            }
            if (counter == 9) {
                return 2;
            }
            return 0;
        }

        public void resultScene(ActionEvent event) throws IOException {
            winner = checkForWinner();
            winner2 = winner;
            switch (winner) {

                case 1:
                    ++TwoPlayers.TwoController.p1score;
                    if (myTurn) {
                        cv.changeScene("/winnerScene/winnerView.fxml", event);
                        dos.writeUTF("winsit:"+player2+":-1");
                        isRunning = false;
                    }
                    break;
                case -1:
                    ++TwoPlayers.TwoController.p2score;
                    if (myTurn) {
                        cv.changeScene("/winnerScene/winnerView.fxml", event);
                        dos.writeUTF("winsit:"+player2+":-1");
                        isRunning = false;
                    }
                    break;
                case 2:
                    if (myTurn) {
                        cv.changeScene("/drawScene/drawView.fxml", event);
                        dos.writeUTF("winsit:"+player2+":0");
                        isRunning = false;
                    }
                    break;

            }

        }
    }

    @Override
    public void run() {
        while (isRunning) {
            try {

                if (dis.available() <= 0) {
                    continue;
                }
                String receivedMsg;

                receivedMsg = dis.readUTF();

                String[] parts = receivedMsg.split("\\:");
                if(parts[0].equals("winner")){
                    if (parts[1].equals("1")){
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                   cv.changeScene("/winnerScene/winnerView.fxml", threadEvent);
                                   isRunning = false;
                                } catch (IOException ex) {
                                    Logger.getLogger(TwoController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });
                    }
                    else if(parts[1].equals("-1")){
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    cv.changeScene("/loserScene/loserView.fxml", threadEvent);
                                    isRunning = false;
                                } catch (IOException ex) {
                                    Logger.getLogger(TwoController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });
                    }
                    else if(parts[1].equals("0")){
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    cv.changeScene("/drawScene/drawView.fxml", threadEvent);
                                    isRunning = false;
                                } catch (IOException ex) {
                                    Logger.getLogger(TwoController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });
                    }
                }
                if (parts[0].equals("move")) {

                    if (parts[1].equals("1")) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    btnChanger(btn1, threadEvent, 0, 0);
                                    System.out.println("btn1 fired");
                                    myTurn = !myTurn;
                                } catch (IOException ex) {
                                    Logger.getLogger(TwoController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });
                    } else if (parts[1].equals("2")) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    btnChanger(btn2, threadEvent, 0, 1);
                                    myTurn = !myTurn;
                                    System.out.println("2");
                                } catch (IOException ex) {
                                    Logger.getLogger(TwoController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });

                    } else if (parts[1].equals("3")) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    btnChanger(btn3, threadEvent, 0, 2);
                                    myTurn = !myTurn;
                                    System.out.println("3");
                                } catch (IOException ex) {
                                    Logger.getLogger(TwoController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });

                    } else if (parts[1].equals("4")) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    btnChanger(btn4, threadEvent, 1, 0);
                                    myTurn = !myTurn;
                                    System.out.println("4");
                                } catch (IOException ex) {
                                    Logger.getLogger(TwoController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });

                    } else if (parts[1].equals("5")) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    btnChanger(btn5, threadEvent, 1, 1);
                                    myTurn = !myTurn;
                                    System.out.println("5");
                                } catch (IOException ex) {
                                    Logger.getLogger(TwoController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });

                    } else if (parts[1].equals("6")) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    btnChanger(btn6, threadEvent, 1, 2);
                                    myTurn = !myTurn;
                                    System.out.println("6");
                                } catch (IOException ex) {
                                    Logger.getLogger(TwoController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });

                    } else if (parts[1].equals("7")) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    btnChanger(btn7, threadEvent, 2, 0);
                                    myTurn = !myTurn;
                                    System.out.println("7");
                                } catch (IOException ex) {
                                    Logger.getLogger(TwoController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });

                    } else if (parts[1].equals("8")) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    btnChanger(btn8, threadEvent, 2, 1);
                                    myTurn = !myTurn;
                                    System.out.println("8");
                                } catch (IOException ex) {
                                    Logger.getLogger(TwoController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });

                    } else if (parts[1].equals("9")) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    btnChanger(btn9, threadEvent, 2, 2);
                                    myTurn = !myTurn;
                                    System.out.println("9");
                                } catch (IOException ex) {
                                    Logger.getLogger(TwoController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });
                    }
                }

            } catch (IOException ex) {
                Logger.getLogger(TwoController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

}
