package RuleSets.Rules;
import Game.*;
import RuleSets.*;
import Pieces.*;
import java.util.Scanner;
public class FiftyMoveRule implements IRule {
    
    //Moves passed since a capture or pawn movement has occured
    private int movesSinceEvent;

    public FiftyMoveRule(){
        movesSinceEvent = 0;
    }
    
    
    public Result checkAndMakeMove(Board board, IRuleSet rules, Move move){
        Result result = checkMove(board, rules, move);
        
        return result;
    }

    public Result checkMove(Board board, IRuleSet rules, Move move){
        
        //If Pawn is being moved
        if (board.getPieceFromPosition(move.getCurrentPosition()) instanceof Pawn)
        {
            if (rules.checkMovePawn(board, move))
            {
                movesSinceEvent = 0;
            }
            else
            {
                return new Result(false, "Generic invalid move");
            }
        }
        //Piece Captured
        else if (move.getPieceCaptured() != null)
        {
            movesSinceEvent = 0;
        }
        else
        {
            movesSinceEvent += 1;
        }
        
        if (movesSinceEvent >= 50)
        {
            return new Result(true, true, "Game Over due to 50 Moves Rule.");
        }
        else
        {
            return new Result(false, "50 turns have not passed since a capture or pawn movement has occured.");
        }
    }
}
