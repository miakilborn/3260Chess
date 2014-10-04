package Pieces;
import Game.Coordinate;
public class Queen extends Piece{
    //this class is intentionally left blank, see Piece
    public Queen(String colour, Coordinate position){
        super.setPosition(position);
        super.setColour(colour);
    }

    public Queen(String s){
        if(s.substring(0,"Queen".length()).equals("Queen")){
            super.setupFromString("Queen",s);
        }
    }
}
