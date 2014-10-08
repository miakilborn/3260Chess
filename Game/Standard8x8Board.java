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
        Piece piece = this.getPieceFromPosition(m.getCurrentPosition());
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

    /*
    * Function returns a string representation of the board
    *
    *
    */
    public String toString(){
        char[][] board = new char[10][19];
        int x, y;
        String strBoard = "";
        char[] numsAcross = {' ', ' ', '1', ' ', '2', ' ', '3', ' ', '4', ' ', '5', ' ', '6', ' ', '7', ' ', '8', ' ', ' '};
        char[] numsDown = {' ', '1', '2', '3', '4', '5', '6', '7', '8', ' '};
        
        //initialize
        for(int i = 0; i < 10; i++){
            if((i == 0) || (i == 9)){
                board[i] = numsAcross;
            } 
            else {
                for(int j = 0; j < 19; j++){
                    //odd cols are dashes
                    if(j % 2 == 1){
                        board[i][j] = ' ';
                    }
                    else {
                        board[i][j] = '-';
                    }
                }
                board[i][0] = numsDown[i];
                board[i][18] = numsDown[i];
            }
        }

        for(Piece p : pieces){
            x = p.getPosition().getX();
            x = x + x;
            
            y = p.getPosition().getY();
            if(p.getColour().equals("White")){
                board[y][x] = Character.toUpperCase(p.toChar());
            } else {
                board[y][x] = p.toChar();
            }
            
        }

        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 19; j++){
                strBoard = strBoard + board[i][j];
            }
            strBoard = strBoard + "\n";
        }

        return strBoard;
    }
}
