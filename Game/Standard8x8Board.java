package Game;
import Pieces.*;
import Game.*;
import java.util.ArrayList;

public class Standard8x8Board implements IBoard{
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

    public void makeMove(Move m){
        return;
    }

    public Piece getPieceAtPosition(Coordinate coord){
        for (int i=0;i<pieces.size();i++){
            Piece piece = pieces.get(i);
            if (piece.getPosition().equals(coord)){
                return piece;
            }
        }
        return null;
    }
}
