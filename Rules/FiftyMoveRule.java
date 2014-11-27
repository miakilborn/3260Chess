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

    @Override
    public Result makeMove(Move move) {
        Result result = rules.makeMove(move);
        if (result.isValid()){
            if (board.getPieceFromPosition(move.getNextPosition()) instanceof Pawn){
                movesSinceEvent = 0;
            } else if (move.getPieceCaptured() != null){
                movesSinceEvent = 0;
            } else {
                movesSinceEvent += 1;
            }
            if (movesSinceEvent >= 5){
                return new Result(true, true, "Game Over due to 50 Moves Rule.");
            }
        }
        return result;
    }
}
