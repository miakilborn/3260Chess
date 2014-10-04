package Game;
import Pieces.*;
import Game.*;
import java.util.ArrayList;

public class Standard8x8Board implements IBoard {
    private ArrayList<Piece> pieces;

    public Standard8x8Board(){
        pieces = new ArrayList<Piece>();
    }

    public void addPiece(Piece p){
        pieces.add(p);
    }

    public ArrayList<Piece> getPieces(){
        return pieces;
    }

    /**
    * Perform a piece move, no checks happen with this method!
    * @param    Move command
    */
    public void makeMove(Move m){
        Piece piece = m.getPiece();
        piece.setPosition(m.getNextPosition());
        piece.setHasMoved(true);
    }

    public Piece getPieceFromPosition(Coordinate coord){
        for (int i=0;i<pieces.size();i++){
            Piece piece = pieces.get(i);
            if (piece.getPosition().equals(coord)){
                return piece;
            }
        }
        return null;
    }
}
