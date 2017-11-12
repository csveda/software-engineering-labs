import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.io.*;
import java.util.*;

/*
Given class
 */

class RPSSkel extends JFrame implements ActionListener {
    private Gameboard myboard, computersboard;
    private int counter; // To count ONE ... TWO  and on THREE you play
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private JButton closebutton;
    // private Integer[][] winMatrix = {{0,-1,1},{1,0,-1},{-1,1,0}};

    RPSSkel () {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        closebutton = new JButton("Close");
        myboard = new Gameboard("Myself", this); // Must be changed

        computersboard = new Gameboard("Computer");
        JPanel boards = new JPanel();
        boards.setLayout(new GridLayout(1,2));
        boards.add(myboard);
        boards.add(computersboard);
        add(boards, BorderLayout.CENTER);
        add(closebutton, BorderLayout.SOUTH);
        setSize(300, 550);
        setVisible(true);

        setupConnection();

    }

    public static void main (String[] u) {
        new RPSSkel();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        counter++;
        myboard.resetColor();
        computersboard.resetColor();

        switch(counter) {
            case 1:
                myboard.setLower("ETT");
                computersboard.setLower("ETT");
                break;
            case 2:
                myboard.setLower("TVÅ");
                computersboard.setLower("TVÅ");
                break;
            case 3:
                myboard.setLower("TRE");
                computersboard.setLower("TRE");

                counter = 0;
                JButton playerButton = (JButton) e.getSource();
                String playerChoice = playerButton.getActionCommand();
                out.println(playerChoice); out.flush();
                try {
                    String compChoice = in.readLine();
                    computersboard.markPlayed(compChoice);
                    myboard.markPlayed(playerChoice);
                    playerWin(playerChoice, compChoice);
                } catch (IOException ioError) {
                    System.out.println(ioError);
                }
        }
     }


    private void playerWin(String playerChoice, String compChoice) {
        // Matrix of win/lose where 1 win, 0 tie and -1 lose
        Integer[][] winMatrix = {{0,-1,1},{1,0,-1},{-1,1,0}};
        int player = mapChoice(playerChoice);
        int computer = mapChoice(compChoice);

        switch (winMatrix[computer][player]) {
            case 1:
                myboard.wins();
                myboard.setUpper("WINS");
                computersboard.setUpper("LOST");
                break;
            case 0:
                myboard.setUpper("TIE");
                computersboard.setUpper("TIE");
                break;
            case -1:
                computersboard.wins();
                myboard.setUpper("LOST");
                computersboard.setUpper("WINS");
        }
    }

    private int mapChoice(String choice) {
        int numChoice = -1;

        switch(choice) {
            case "STEN":
                numChoice = 0;
                break;
            case "SAX":
                numChoice = 1;
                break;
            case "PASE":
                numChoice = 2;
                break;
        }

        if (numChoice == -1) {
            throw new java.lang.Error("Something went wrong");
        }

        return numChoice;
    }

    private void setupConnection() {
        try {
            socket=new Socket("share-02.csc.kth.se",4713);
            in=new BufferedReader
                    (new InputStreamReader(socket.getInputStream()));
            out=new PrintWriter(socket.getOutputStream());
            out.println("Charlotta"); out.flush();
            System.out.println(in.readLine());
            //out.println("Charlotta"); out.flush();
            //System.out.println(in.readLine());
            //out.println("STEN"); out.flush();
            //System.out.println(in.readLine());
        } catch (IOException e) {
            System.out.println(e);
        }

    }

}



