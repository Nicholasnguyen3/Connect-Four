package hw4;

import hw4.CFGame;
import hw4.CFPlayer;
import hw4.RandomAI;
import hw4.WilliamAI;

import javax.swing.BorderFactory;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUICF extends CFGame
{

    // declare the GameBoard variable
    private GameBoard board;

    // Declare variables used in the game
    private CFPlayer red_player;
    private CFPlayer black_player;
    private String winner_name;
    boolean end_game;
    private int column_played;
    int count = 0;
    boolean humanP1;

    // define GUI elements
    private Container pane;
    private JButton[] jBtns = new JButton[7];
    private JFrame jframe = new JFrame("Connect Four");
    private JPanel jLabelPanel = new JPanel();
    private JLabel[][] cells = new JLabel[7][6];
    private JButton playButton = new JButton("play");


    // constructor for game with 1 AI and 1 human player where first player (red) is randomly decided
    public GUICF(CFPlayer ai) {
    
        // initialize and set up the board
        board = new GameBoard();
        pane = jframe.getContentPane();
        JPanel buttonpanel = new JPanel();
        buttonpanel.setLayout(new GridLayout(1, 7, 50, 10));

        // initialize the buttons
        for (int i = 0; i < jBtns.length; i++) {
            jBtns[i] = new JButton("\u2193");
            jBtns[i].addActionListener(new SlotButton());
            buttonpanel.add(jBtns[i]);
        }

        // add buttons to board
        pane.add(buttonpanel, BorderLayout.NORTH);

        // initiates human player
        HumanPlayer human = new HumanPlayer();

        // initiates random number generator
        Random rand = new Random();
        int r = rand.nextInt(2);
            
        // randomly assigns players to red or black
        if (r==0) {
            red_player = human;
            black_player = ai;
            humanP1 = true;
        }
        else {
            black_player = human;
            red_player = ai;
            humanP1 = false;
        }
        
        // plays first round for ai if ai was selected to go first
        boolean valid_move = false;
        int move;
        
        if (red_player == ai) {
            while (valid_move == false) {
                move = red_player.nextMove(this);
                valid_move = this.playGUI(move);
            }
        }
    }

    
    // constructor for game with 2 AIs where first player (red) is randomly decided
    public GUICF(CFPlayer ai1, CFPlayer ai2) {

        // initialize and set up the GameBoard
        board = new GameBoard();
        pane = jframe.getContentPane();
        playButton.addActionListener(new ButtonPlay());
        playButton.setPreferredSize(new Dimension(10, 20));
        pane.add(playButton, BorderLayout.NORTH);

        // initiates random number generator
        Random rand = new Random();
        int r = rand.nextInt(2);
        
        // randomly assigns players to red or black
        if (r==0) {
            red_player = ai1;
            black_player = ai2;
        }
        else {
            black_player = ai1;
            red_player = ai2;
        }
    }

    // event class takes value human player selected to play and then launches play function
    private class SlotButton implements ActionListener {
  
        @Override
        public void actionPerformed(ActionEvent e) {
                        
            for (int i = 0; i < jBtns.length; i++) {
                if (jBtns[i] == e.getSource()) {
                    if (end_game == false) {
                        column_played = i;
                    }
                }
            }
            
            human_play();
        }
    }

    // event class to determine if play button has been pressed in AI vs AI game
    private class ButtonPlay implements ActionListener {
    
        @Override
        public void actionPerformed(ActionEvent e) {
            // plays round if play button was pressed
            ai_play();
        }
    }

    // nested class used to build game board
    private class GameBoard extends JPanel
    {
        // constructor
        public GameBoard()
        {
            // set up board and board parameters
            jframe.setVisible(true);
            jframe.setSize(800, 500);
            jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            pane = jframe.getContentPane();
            jLabelPanel.setLayout(new GridLayout(6, 7));

            // initialize labels for each slot on the board
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 6; j++) {
                    cells[i][j] = new JLabel("");
                    cells[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
                }
            }

            // add the slots to the jLabel panel
            for (int j = 5; j >= 0; j--) {
                for (int i = 0; i < 7; i++) {
                    jLabelPanel.add(cells[i][j]);
                }
            }

            // add the jLabel panel to the pane
            pane.add(jLabelPanel, BorderLayout.CENTER);
        }

        // method to color the labels
        private void paint(int x, int y, int color) {
            // if the color value is 1, set the label at the (x, y) cell to red
            if (color == 1) {
                cells[x][y].setBackground(Color.red);
                cells[x][y].setOpaque(true);
            }

            // if the color is -1, set the label at (x, y) cell to black
            else if (color == -1) {
                cells[x][y].setBackground(Color.black);
                cells[x][y].setOpaque(true);
            }
        }
    }

    // playGUI method updates game object and board
    private boolean playGUI(int c) {
        // if the call the super class play method by passing the column number if it is free update the game
        if (this.play(c)) {

            // define an array to hold the copy of the game
            int[][] copy_GUI = new int[7][6];

            // get the state of the board
            copy_GUI = this.getState();

            // paint the board accordingly
            for (int j = 5; j >= 0; j--) {
                if (copy_GUI[c][j] != 0) {
                    if (copy_GUI[c][j] == 1)
                        board.paint(c, j, 1);
                    else
                        board.paint(c, j, -1);

                }
            }
            return true;
        }
        
        else
            return false;
}

    // private class for Human Player
    private class HumanPlayer implements CFPlayer {
        
        // method used to find human player's next move
        public int nextMove(CFGame g) {

                // set the value of the column played into temp
                int temp = column_played;

                // reset the column_played to zero
                column_played = 0;
            
                // return the value of the column played
                return (temp);
        }
        

        // method to return the name of the player
        public String getName()
        {
            return ("Human Player");
        }
    }

    // function to play round for human vs ai game
    public void human_play() {
        
        // sets variables to be used later on
            boolean valid_move = false;
            int move;
            
        // plays move for human and ai if human is first player
            if (humanP1 == true) {
                move = red_player.nextMove(this);
                valid_move = this.playGUI(move);
                if (valid_move == false) {
                    System.out.println("invalid move");
                    return;
                }
                
                valid_move = false;
                
                while (valid_move == false) {
                    move = black_player.nextMove(this);
                    valid_move = this.playGUI(move);
                }
    }
    
        // plays move for human and ai if human is second player
            if (humanP1 == false) {
                move = black_player.nextMove(this);
                valid_move = this.playGUI(move);
                if (valid_move == false) {
                    System.out.println("invalid move");
                    return;
                }
                
                valid_move = false;
                
                while (valid_move == false) {
                    move = red_player.nextMove(this);
                    valid_move = this.playGUI(move);
                }
            }
                
        // checks to see if game is over
        boolean game_over = this.isGameOver();
        
        // if game is over ouputs winner or draw
        if (game_over == true) {
            
            // sets winner's name into Class field
            end_game = true;
            int winner = this.winner();
            
            if (winner == 1)
                winner_name = red_player.getName();
            if (winner == -1)
                winner_name = black_player.getName();
            if (winner == 0)  {
                winner_name = "Draw";
                System.out.println("Game Over. Draw.");
            }

            System.out.println("Game Over. " + winner_name + " wins");
        }
        
    }
    
    public void ai_play() {
        
        // sets variables to be used later on
            boolean valid_move = false;
            int move;
                
        // plays turn for whichever AI is meant to play that turn
            if (count % 2 == 0) {
                while (valid_move == false) {
                    move = red_player.nextMove(this);
                    valid_move = this.playGUI(move);
                }
                count++;
            }
                
            else {
                while (valid_move == false) {
                        move = black_player.nextMove(this);
                        valid_move = this.playGUI(move);
                    }
                    count++;
                }
        
        // checks to see if game is over
        boolean game_over = this.isGameOver();
        
        // if game is over ouputs winner or draw
        if (game_over == true) {
            
            // sets winner's name into Class field
            end_game = true;
            int winner = this.winner();
            
            if (winner == 1)
                winner_name = red_player.getName();
            if (winner == -1)
                winner_name = black_player.getName();
            if (winner == 0)  {
                winner_name = "Draw";
                System.out.println("Game Over. Draw.");
            }

            System.out.println("Game Over. " + winner_name + " wins");
        }
    }
    
}
