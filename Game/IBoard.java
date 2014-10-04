package Game;
import Pieces.*;
import java.util.ArrayList;
public interface IBoard {
    //List of pieces
    public void addPiece(Piece p);
    public ArrayList<Piece> getPieces();
}
