package RuleSets.Rules;
import Game.*;
import Pieces.*;
public class Promotion implements IRules {

    public Promotion(){

    }


    public Result checkMove(IBoard board, Move move){
        Piece piece = board.getPieceFromPosition(move.getNextPosition());
        Result result = null;
        if (piece instanceof Pawn){
                if (piece.getColour().equals("White")){

                }

        } else {
                return new Result(true);
        }
        return null;
    }
}