package Game;
import Pieces.*;
import java.util.ArrayList;
public interface Board {
    //List of pieces
    public boolean addPiece(Piece p);
    public void removePiece(Piece p);
    public ArrayList<Piece> getPieces();
    public Move getLastMove();
    public void setLastMove(Move move);

    public Piece getPieceFromPosition(Coordinate coord);

    public boolean isValidCoordinate(Coordinate coord);

    public void performMove(Move move);
    public String toString();

}
