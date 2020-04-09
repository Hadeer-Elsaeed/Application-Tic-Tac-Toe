package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayerHandler extends Thread {

    private final int idCounter;
    private String playerName;
    private boolean available;
    private final Socket clientSocket;
    PreparedStatement pst;
    ResultSet rs;
    DataInputStream dis;
    DataOutputStream dos;
    Connection conn;    //connect to db

    String[] str;

    public int getIdCounter() {
        return idCounter;
    }

    public String getPlayerName() {
        return playerName;
    }

    static Vector<PlayerHandler> playerVector = new Vector();

    public PlayerHandler(Socket cs, int id) throws IOException {
        this.clientSocket = cs;
        this.idCounter = id;
        System.out.println(cs.getPort());
        dis = new DataInputStream(cs.getInputStream());
        dos = new DataOutputStream(cs.getOutputStream());
        playerName = "";
        playerVector.add(this);

        start();
    }

    public void run() {
        while (true) {
            try {
                String msg;
                if (dis.available() <= 0) {
                    continue;
                }
                try {
                    msg = dis.readUTF();
                    str = msg.split("\\:");
                    System.out.println(str[0]);
                    if ("login".equals(str[0]) && str.length >= 3) {
                        //call fn authentication
                        boolean flag = authentication(str[1], str[2]);
                        if (flag) {
                            // ur code
                            System.out.println(flag);
                            //setting player name after login
                            playerVector.get(idCounter).setPlayerName(str[1]);
                            String pname = playerName;
                            System.out.println(playerName);
                            for (PlayerHandler ch : playerVector) {
                                if (ch.playerName.equals(str[1])) {
                                    ch.dos.writeUTF("loggedin:yes");
                                }
                            }
                            sendMyNameToAll(pname);
//                            sleep(500);
//                            sendAllNamesToMe(pname);

                        } else {
                            // ur code
                            for (PlayerHandler ch : playerVector) {
                                ch.dos.writeUTF("loggedin:no");
                                System.out.println("loggedin:no");
                            }
                            System.out.println(flag);
                        }
                    }

                    if ("signup".equals(str[0])) {
                        //call fn registeration
                        boolean flag = registeration(str[1], str[2]);
                        if (flag) {
                            // ur code
                            System.out.println(flag);
                            //setting player name after login
                            playerVector.get(idCounter).setPlayerName(str[1]);
                            String pname = playerName;
                            System.out.println(pname);
                            playerVector.get(idCounter).dos.writeUTF("signedup:yes");
                            System.out.println("signedup:yes");
                            sendMyNameToAll(pname);
//                            sleep(500);
//                            sendAllNamesToMe(pname);
                        } else {
                            playerVector.get(idCounter).dos.writeUTF("signedup:no");
                            System.out.println("signedup:no");
                        }
                    }
                    if ("move".equals(str[0])) {
                        sendMoveToPlayer(str[1], str[2]);
                    }
                    if ("playReq".equals(str[0])) {
                        sendReqToPlayer(str[1], str[2], str[3]);
                    }
                    if ("acceptedReq".equals(str[0])) {
                        acceptanceConfirmed(str[1], str[2]);
                    }
                    if ("cancelReq".equals(str[0])) {
                        sendCancelReqToPlayer(str[1]);
                    }
                    if ("pNamesAgain".equals(str[0])){
                        sendAllNamesToMe(str[1]);
                    }
                } catch (Exception ex) {
                    try {
                        dos.close();
                        dis.close();
                        clientSocket.close();
                        System.out.println(ex.getMessage());
                        System.out.println("dis.readUTF is failed to read and we close the client connection");
                        break;

                    } catch (IOException ex1) {
                        System.out.println(ex.getMessage());
                        Logger.getLogger(PlayerHandler.class.getName()).log(Level.SEVERE, null, ex1);
                        System.out.println("closing the connection failed");
                    }
                    System.out.println(ex.getMessage());
                    System.out.println("readUTF is failed");

                }

            } catch (IOException ex) {
                System.out.println("dis.available is closed");
                Logger.getLogger(PlayerHandler.class.getName()).log(Level.SEVERE, null, ex);

            }

        }
    }

    public void setPlayerName(String name) {
        this.playerName = name;
    }

    //send player name to all players
    public void sendMyNameToAll(String myName) throws IOException {
        for (PlayerHandler ch : playerVector) {
            if (!(ch.playerName.equals(myName))) {
                System.out.println("send my name to all :"+myName);
                ch.dos.writeUTF("pname:"+myName);
            }
        }
        System.out.println(myName);
    }

    //send all player names to me
    public synchronized void sendAllNamesToMe(String myName) throws IOException {
        String pnames = "pnames";
        for (PlayerHandler ch : playerVector) {
            if (!(ch.playerName.equals(myName)) && !(ch.playerName.equals(""))) {
                pnames = pnames + ":" + ch.playerName;
            }
        }
        for (PlayerHandler ch : playerVector) {
            if (ch.playerName.equals(myName)) {
                System.out.println(pnames);
                ch.dos.writeUTF(pnames);
                break;
            }
        }
    }

    //Send a move to a the other player
    public void sendMoveToPlayer(String number, String name) throws IOException {
        for (PlayerHandler ch : playerVector) {
            if (ch.playerName.equals(name)) {
                ch.dos.writeUTF("move:" + number);
                break;
            }
        }
        System.out.println("move:" + number);
    }

    //send request to other player
    public void sendReqToPlayer(String p1Name, String p2Name, String urplay) throws IOException {
        for (PlayerHandler ch : playerVector) {
            if (ch.playerName.equals(p2Name)) {
                ch.dos.writeUTF("request:" + p1Name + ":" + urplay);
                break;
            }
        }
        System.out.println("request:" + p1Name + ":" + urplay);
    }

    //accepted request from the other player
    public void acceptanceConfirmed(String p1Name, String accept) throws IOException {
        for (PlayerHandler ch : playerVector) {
            if (ch.playerName.equals(p1Name)) {
                ch.dos.writeUTF("acceptance:" + accept);
                break;
            }
        }
        System.out.println("acceptance:" + accept);
    }

    //Send a cancelReq to a the other player
    public void sendCancelReqToPlayer(String pname) throws IOException {
        for (PlayerHandler ch : playerVector) {
            if (ch.playerName.equals(pname)) {
                ch.dos.writeUTF("reqCanceled");
                break;
            }
        }
        System.out.println("req:canceled" + pname);
    }

    // Connect to Database and check for the username and passward
    //Don't forget to close ur Database connection after executing ur query
    public boolean authentication(String username, String password) throws SQLException {

        conn = getConnection();
        pst = conn.prepareStatement("SELECT username,password FROM mytable WHERE username=? AND password=?");
        pst.setString(1, username);
        pst.setString(2, password);
        rs = pst.executeQuery();
        if (rs.next()) {
            System.out.println("match");
            conn.close();
            System.out.println("database connection closed");
            return true;
        } else {
            System.out.println("Not match");
            System.out.println("database connection closed");
            conn.close();
            return false;
        }

    }

    // Connect to Database and insert a new username and passward
    //Don't forget to close ur Database connection after executing ur query
    public boolean registeration(String username, String password) throws SQLException {

        conn = getConnection();
        pst = conn.prepareStatement("SELECT username FROM mytable WHERE username =  ?");
        pst.setString(1, username);
        rs = pst.executeQuery();
        if (rs.next()) {
            System.out.println("This user is signed up before");
            conn.close();
            System.out.println("database connection closed");
            return false;
        } else {
            pst = conn.prepareStatement("INSERT INTO mytable(username,password)values(?,?)");
            pst.setString(1, username);
            pst.setString(2, password);
            pst.executeUpdate();
            conn.close();
            System.out.println("database connection closed");
            System.out.println("new user has been added successfully");
            return true;
        }
    }

    //connection to database
    private static Connection getConnection() {
        Connection con = null;
        String url = "jdbc:mysql://localhost:3306/";
        String db = "xogame";
        String driver = "com.mysql.cj.jdbc.Driver";
        String user = "root";
        String pass = "It@chisa7by";
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url + db, user, pass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(con);
        return con;
    }
}
