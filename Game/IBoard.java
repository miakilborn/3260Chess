package Game;
import Pieces.*;
import java.util.ArrayList;
public interface IBoard {    
    //List of pieces
    public boolean addPiece(Piece p);
    public void removePiece(Piece p);
    public ArrayList<Piece> getPieces();

    public Piece getPieceFromPosition(Coordinate coord);

}
