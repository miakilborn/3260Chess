package RuleSets.Rules;
import Game.*;
import RuleSets.*;
import Pieces.*;
public class EnPassent implements IRule {

    public EnPassent(){

    }
    public Result checkAndMakeMove(IBoard board, IRuleSet rules, Move move){
        Move lastMove = rules.getLastMove();
        if(lastMove==null){
            return new Result(false);
        }

        Coordinate current = move.getCurrentPosition();
        Coordinate lastNextP = lastMove.getNextPosition();
        if(current==null || lastNextP==null){
            return new Result(false);
        }
        Piece myPiece = board.getPieceFromPosition(current);
        Piece theirPiece = board.getPieceFromPosition(lastNextP);
        if(myPiece==null || theirPiece==null){
            return new Result(false);
        }

        if(checkMove(board,rules,move).isValid()){
            theirPiece.capture();
            myPiece.setPosition(move.getNextPosition());
            return new Result(true);
        }

        return new Result(false);
    }

    public Result checkMove(IBoard board, IRuleSet rules, Move move){
        Move lastMove = rules.getLastMove();
        if(lastMove==null){
            return new Result(false);
        }
        Coordinate current = move.getCurrentPosition();
        Coordinate newP = move.getNextPosition();
        Coordinate lastNextP = lastMove.getNextPosition();
        Coordinate lastCurrent = lastMove.getCurrentPosition();
        if(current==null || newP==null || lastNextP == null || lastCurrent==null){
            return new Result(false);
        }
        Piece myPiece = board.getPieceFromPosition(current);
        Piece theirPiece = board.getPieceFromPosition(lastNextP);
        if(myPiece==null || theirPiece==null){
            return new Result(false);
        }

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
            if((lastNextP.getY()==5 && newP.getY()==6 && lastNextP.getX()==newP.getX())
            || (lastNextP.getY()==4 && newP.getY()==3 && lastNextP.getX()==newP.getX())){
                return new Result(true);
            }
        }

        return new Result(false);
    }
}
