package Game;
import Pieces.*;
import Game.*;
import java.util.ArrayList;

public class Standard8x8Board implements Board {
    private ArrayList<Piece> pieces;

    public Standard8x8Board(){
        pieces = new ArrayList<Piece>();
    }
    
    public Standard8x8Board(String boardStr){
        pieces = new ArrayList<Piece>();
        while (boardStr.length() > 1){
            String pieceStr = boardStr.substring(boardStr.indexOf("|(")+2, boardStr.indexOf(")|"));
            if (pieceStr.contains("Queen")){
                pieces.add(new Queen(pieceStr));
            } else if (pieceStr.contains("King")){
                pieces.add(new King(pieceStr));
            } else if (pieceStr.contains("Bishop")){
                pieces.add(new Bishop(pieceStr));
            } else if (pieceStr.contains("Knight")){
                pieces.add(new Knight(pieceStr));
            } else if (pieceStr.contains("Rook")){
                pieces.add(new Rook(pieceStr));
            } else if (pieceStr.contains("Pawn")){
                pieces.add(new Pawn(pieceStr));
            }
            boardStr = boardStr.substring(boardStr.indexOf(")|")+1, boardStr.length());
        }
    }

    public boolean addPiece(Piece p){
        return pieces.add(p);
    }
    
    public void removePiece(Piece p){
        pieces.remove(p);
    }

    public ArrayList<Piece> getPieces(){
        return pieces;
    }

    public Piece getPieceFromPosition(Coordinate coord){
        if (coord == null){
            return null;
        }
                
        for (int i=0;i<pieces.size();i++){
            Piece piece = pieces.get(i);
            if (piece.getPosition().equals(coord)){
                return piece;
            }
        }
        return null;
    }

    public String toString(){
        String ret = this.getClass().getName() + "|";
        for(Piece p : pieces){
            ret += "("+p.toString()+")|";
        }
        return ret;
    }

    /*
    * Function returns a formatted string representation of the board
    *
    *
    */
    public String toFormattedString(){
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

    @Override
    public boolean isValidCoordinate(Coordinate coord) {
        if (coord != null){
            int x = coord.getX();
            int y = coord.getY();
            if (x > 0 && x <=8 && y > 0 && y <= 8)
                return true;
        }
        return false;
    }

    @Override
    public void performMove(Move move) {
        Piece p1 = this.getPieceFromPosition(move.getCurrentPosition());
        Piece capture = this.getPieceFromPosition(move.getNextPosition());
        if (capture != null){
            capture.capture();
        }
        p1.setPosition(move.getNextPosition());
    }
}
