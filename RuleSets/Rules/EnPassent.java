package RuleSets.Rules;
import Game.*;
import RuleSets.*;
import Pieces.*;
public class EnPassent implements IRule {

    public EnPassent(){

    }
    public Result checkAndMakeMove(IBoard board, IRuleSet rules, Move move){
        Coordinate current = move.getCurrentPosition();
        Coordinate newP = move.getNextPosition();
        Piece myPiece = board.getPieceFromPosition(current);
        Move lastMove = rules.getLastMove();
        Coordinate lastNextP = lastMove.getNextPosition();
        Coordinate lastCurrent = lastMove.getCurrentPosition();
        Piece theirPiece = board.getPieceFromPosition(lastNextP);

        if(!(myPiece instanceof Pawn) || !(theirPiece instanceof Pawn)){
            return new Result(false);
        }

        //if last move was double jump pawn
        if(Math.abs(lastNextP.getY()-lastCurrent.getY())!=2){
            return new Result(false);
        }

        //if desired move is on an angle
        if(Math.abs(current.getY()-newP.getY())==1 && Math.abs(current.getX()-newP.getX())==1){
            //if desired move puts pawn behind other pawn
            if(Math.abs(current.getY()-lastNextP.getY())==1){
                return new Result(true);
            }
        }

        return new Result(false);
    }

    public Result checkMove(IBoard board, IRuleSet rules, Move move){
        return new Result(false);
    }
}
