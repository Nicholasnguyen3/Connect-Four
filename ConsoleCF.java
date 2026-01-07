package hw4;

import java.util.Random;
import java.util.Scanner;
import hw4.CFPlayer;
import hw4.CFGame;
import hw4.RandomAI;
import hw4.WilliamAI;

public class ConsoleCF extends CFGame {
    
    // class fields
    private CFPlayer red_player;
    private CFPlayer black_player;
    private String winner_name;
    private int count = 0;
    
    // constructor for game with 1 AI and 1 human player where first player (red) is randomly decided
    public ConsoleCF(CFPlayer ai) {
        
        // initiates human player
        HumanPlayer human = new HumanPlayer();

        // initiates random number generator
        Random rand = new Random();
        int r = rand.nextInt(2);
        
        // randomly assigns players to red or black
        if (r==0) {
            red_player = human;
            black_player = ai;
        }
        else {
            black_player = human;
            red_player = ai;
        }
    }
    
    // constructor for game with 2 AIs where first player (red) is randomly decided
    public ConsoleCF(CFPlayer ai1, CFPlayer ai2) {
        
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

    // plays the game until the game is over
    public void playOut () {
        
        // initializes game object
        CFGame g = new CFGame();
        
        // sets variables to be used later on
        boolean game_over = false;
        boolean valid_move = false;
        int move;
        int winner;
        
        // runs moves for each player until game is over
        while (game_over == false) {
            
            if (count % 2 == 0) {
                while (valid_move == false) {
                    move = red_player.nextMove(g);
                    valid_move = g.play(move);
                }
                count++;
                valid_move = false;
                game_over = g.isGameOver();
            }
            
            else {
                while (valid_move == false) {
                        move = black_player.nextMove(g);
                        valid_move = g.play(move);
                    }
                    count++;
                valid_move = false;
                    game_over = g.isGameOver();
                }
        }
        
        // sets winner's name into Class field
        winner = g.winner();
        
        if (winner == 1)
            winner_name = red_player.getName();
        if (winner == -1)
            winner_name = black_player.getName();
        if (winner == 0)
            winner_name = "Draw";
    }

    // returns winner's name
    public String getWinner() {
        return winner_name;
    }

    // private class for Human Player
    private class HumanPlayer implements CFPlayer {
    
        // method used to find human player's next move
        public int nextMove(CFGame g){
        
            // sets variables to be used later on
            int index;
            int column = 0;
            int[][] board = g.getState();
            boolean valid_move = false;
        
            // loop outputs current board and asks for next move from human until they input a valid move
            while (valid_move == false) {
        
                // outputs current board status
                for (int i = 6; i >= 0; i--) {
                    for (int j= 5; j >= 0; j--) {
                        index = board[i][j];
                        System.out.print(index);
                    }
                    System.out.println();
                }
        
                // asks human player for next move
                System.out.println("Please input column for your next move");
        
                // inputs value and checks to see if move is valid
                java.util.Scanner reader = new Scanner(System.in);
                column = reader.nextInt()-1;
                valid_move = g.is_valid(column);
            
                // if move invalid asks player to input different move
                if (valid_move == false)
                    System.out.println("move invalid try a different column");
            }
        
            return column;
        }
    
    
        //return human player name
        public String getName() {
            return "Human Player";
        }
    }
}
