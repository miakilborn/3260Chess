package RuleSets.Rules;
import Game.*;
import RuleSets.*;
import Pieces.*;
import java.util.Scanner;
public class FiftyMoveRule implements IRule {

    /*
        Each move, a counter must be increased if a capture did not occur
        and a pawn was not moved.
        
        If either did occur, the counter must be reset
        
        If the counter reaches 50, a result with gameover set to true and appropriate
        message is returned, and he game ends.
    */

    public FiftyMoveRule(){

    }
    
    

    
    public Result checkAndMakeMove(IBoard board, IRuleSet rules, Move move){
        
        return new Result(false);
    }

    public Result checkMove(IBoard board, IRuleSet rules, Move move){
        
        
        return new Result(false, true, "Game Over due to 50 Moves rule.");
    }
}
