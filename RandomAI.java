package hw4;

import java.util.Random;
import hw4.CFPlayer;
import hw4.CFGame;

public class RandomAI implements CFPlayer {

    // randomly picks a move for the AI without actually playing it
    public int nextMove(CFGame g){
        
        Random rand = new Random();
        int column = 0;
        boolean valid_move = false;
                
        // checks if move is valid
        while (valid_move == false) {
            column = rand.nextInt(7);
            valid_move = g.is_valid(column);
        }
        
        return column;
    }
    
    //returns RandomAI name 
    public String getName() {
        return "Random Player";
    };

}
