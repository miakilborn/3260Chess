package Rules;

import Game.*;
import Pieces.*;
import java.util.Scanner;

/**
 *
 * @author Tim
 */
public class FiftyMoveRule extends RulesDecorator {
    private Rules rules;
    private short movesSinceEvent;
    
    public FiftyMoveRule(Rules rules){
        this.rules = rules;
        this.board = rules.board;
    }
    
    /**
     * Performs all checks to see if move if valid before making the move
     * @param move
     * @return validity of the move for this rule
     */
    private Result checkMove(Move move){
         //If Pawn is being moved
        if (board.getPieceFromPosition(move.getCurrentPosition()) instanceof Pawn){
            Result result = rules.checkMovePawn(move);
            if (result.isValid()){
                movesSinceEvent = 0;
            } else {
                return result;
            }
        }
        //Piece Captured
        else if (move.getPieceCaptured() != null){
            movesSinceEvent = 0;
        } else {
            movesSinceEvent += 1;
        }
        
        if (movesSinceEvent >= 50){
            return new Result(true, true, "Game Over due to 50 Moves Rule.");
        } else {
            return new Result(false, "50 turns have not passed since a capture or pawn movement has occured.");
        }
    }

    @Override
    public Result makeMove(Move move) {
        Result result = checkMove(move); //Do promotion checks
        if (!result.isValid()){ //this rule isn't applicable, try another rule
            result = rules.makeMove(move);
        }
        return result;
    }
}
