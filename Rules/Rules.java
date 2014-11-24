package Rules;

import Game.*;
import Pieces.*;
import java.util.ArrayList;

/**
 *
 * @author Tim
 */
public abstract class Rules {
    Board board =  null;
    
    public Result makeMove(Move move) {
        return new Result(false, "Move not made");
    }
    
    public Result checkMovePawn(Move move){
        return new Result(false, "Rules undefined");
    }
    
    public Result checkMoveKing(Move move){
        return new Result(false, "Rules undefined");
    }
    
    public Result checkMoveQueen(Move move){
        return new Result(false, "Rules undefined");
    }
    
    public Result checkMoveBishop(Move move){
        return new Result(false, "Rules undefined");
    }
    
    public Result checkMoveRook(Move move){
        return new Result(false, "Rules undefined");
    }
    
    public Result checkMoveKnight(Move move){
        return new Result(false, "Rules undefined");
    }
    
    /**
    * Creates Pieces for the board, and sets up all their positions
    * @author	Tim
    */
    public void setupBoard(Board board){
        ArrayList<Piece> pieces = new ArrayList<Piece>();
        String colour = "";

        colour = "White";
//        pieces.add(new Rook(colour, new Coordinate(1,1)));
//        pieces.add(new Rook(colour, new Coordinate(8,1)));
//        pieces.add(new Knight(colour, new Coordinate(2,1)));
//        pieces.add(new Knight(colour, new Coordinate(7,1)));
//        pieces.add(new Bishop(colour, new Coordinate(3,1)));
//        pieces.add(new Bishop(colour, new Coordinate(6,1)));
//        pieces.add(new Queen(colour, new Coordinate(4,1)));
//        pieces.add(new King(colour, new Coordinate(5,1)));
        for (int p=1;p<=4;p++)
            pieces.add(new Pawn(colour, new Coordinate(p,2)));

        colour = "Black";
//        pieces.add(new Rook(colour, new Coordinate(1,8)));
//        pieces.add(new Rook(colour, new Coordinate(8,8)));
//        pieces.add(new Knight(colour, new Coordinate(2,8)));
//        pieces.add(new Knight(colour, new Coordinate(7,8)));
//        pieces.add(new Bishop(colour, new Coordinate(3,8)));
//        pieces.add(new Bishop(colour, new Coordinate(6,8)));
//        pieces.add(new Queen(colour, new Coordinate(4,8)));
//        pieces.add(new King(colour, new Coordinate(5,8)));
        for (int p=5;p<=8;p++)
            pieces.add(new Pawn(colour, new Coordinate(p,2)));

        for (int i=0;i<pieces.size();i++)
            board.addPiece(pieces.get(i));
    }
}
