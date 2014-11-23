package Rules;

import Game.*;
import Pieces.*;

/**
 *
 * @author Tim
 */
public class Castling extends RulesDecorator {
    private Rules rules;
    
    public Castling(Rules rules){
        this.rules = rules;
        this.board = rules.board;
    }
    
    /**
     * Performs all checks to see if move if valid before making the move
     * @param move
     * @return validity of the move for this rule
     */
    private Result checkMove(Move move){
        Piece piece1 = board.getPieceFromPosition(move.getCurrentPosition());
        Piece piece2 = board.getPieceFromPosition(move.getNextPosition());
        
        //Check that both positions have a piece
        if (piece1 == null || piece2 == null){
            return new Result(false, "Castling requires a Rook and a King");
        }
        
        //Check if player owns both pieces
        if (!piece1.getColour().equals(piece2.getColour())){
            return new Result(false, "Piece ownership mismatch");
        }
        
        //Check that the piece types are valid for this Rule, the section is NOT'ed
        if (!((piece1 instanceof King || piece1 instanceof Rook) && //one piece is NOT a rook or king
                (piece2 instanceof King || piece2 instanceof Rook) && //other piece is NOT a rook or king
                    (!piece1.getClass().getName().equals(piece2.getClass().getName())))){ //both same type of piece (ex both rooks)
            return new Result(false, "Pieces do not qualify for Castling");
        }
        
        
        //Check that the pieces have not been moved
        if (piece1.hasMoved() || piece2.hasMoved()){
            return new Result(false, "One of these pieces has already moved before");
        }
        
        //Check that there is clearance between the pieces
        int startPos = (piece1.getPosition().getX() > piece2.getPosition().getX()) ? piece2.getPosition().getX() : piece1.getPosition().getX();
        int endPos = (piece1.getPosition().getX() > piece2.getPosition().getX()) ? piece1.getPosition().getX() : piece2.getPosition().getX();
        for (int i=startPos+1;i<endPos;i++){
            Coordinate tempPos = new Coordinate(i, piece1.getPosition().getY());
            if (board.getPieceFromPosition(tempPos) != null){
                return new Result(false, "A piece is in the way for Castling");
            } 
        }
        return new Result(true, "Castling valid");
    }

    @Override
    public Result makeMove(Move move) {
        Result result = checkMove(move); //Do promotion checks
        if (!move.getMoved() && result.isValid()){ //perform the move
            Coordinate piece1Pos = move.getCurrentPosition();
            Coordinate piece2Pos = move.getNextPosition();
            Piece piece1 = board.getPieceFromPosition(piece1Pos);
            Piece piece2 = board.getPieceFromPosition(piece2Pos);
            piece1.setPosition(piece2Pos);
            piece2.setPosition(piece1Pos);
            piece1.setHasMoved(true);
            piece2.setHasMoved(true);
            move.setMoved(true);
            rules.makeMove(move);
        } else { //this rule isn't applicable, try another rule
            result = rules.makeMove(move);
        }
        return result;
    }
    
    
}
