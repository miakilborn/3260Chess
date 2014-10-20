package RuleSets.Rules;
import Game.*;
import RuleSets.*;
import Pieces.*;
import java.util.Scanner;
public class StaleMate implements IRule {

    public Stalemate(){

    }
    
    

    
    public boolean checkAndMakeMove(IBoard board, IRuleSet rules, Move move){
        Piece newPiece = null;
        if (checkMove(board, rules, move).isValid()){
            Piece piece = board.getPieceFromPosition(move.getCurrentPosition());
            Piece cap = board.getPieceFromPosition(move.getNextPosition());
            if(cap != null){
                System.err.println("REMOVING: "+cap.toString());
                board.removePiece(cap);
            }
            
            newPiece = promptPromotion(piece.getColour());
            newPiece.setPosition(move.getNextPosition());
            board.removePiece(piece);
            board.addPiece(newPiece);
            return true;
        }
        return false;
    }

    public Result checkMove(IBoard board, IRuleSet rules, Move move){
        Piece piece = board.getPieceFromPosition(move.getCurrentPosition());
        
        //Verify is a pawn
        if (!(piece instanceof Pawn)){
            return new Result(false, "Piece is not a pawn");
        }
        
        //Verify movement position is a promotional position
        if (!checkPosition(piece.getColour(), move.getNextPosition())){
            return new Result(false, "Not a promotional position");
        }
        
        //Check if move is a valid move
        if (rules.checkMovePawn(board, move)){
            return new Result(true);
        } else {
            return new Result(false, "Generic invalid move");
        }
    }
}
