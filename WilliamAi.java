package hw4;

import java.util.Random;
import hw4.CFPlayer;
import hw4.CFGame;

public class WilliamAI implements CFPlayer {

    // picks a move for the AI without actually playing it
    public int nextMove(CFGame g){
        Random rand = new Random();
        int column;
        boolean valid_move = false;
        
        // checks to see if there is a winning move that can be played
        column = g.winning_move();
        
        // if no winning move can be played find a random valid move
        if (column == 8) {
            while (valid_move == false) {
                column = rand.nextInt(7);
                valid_move = g.is_valid(column);
            }
        }
        
        return column;
    }
    
    //return name of AI
    public String getName() {
        return "William's AI";
    };
}
