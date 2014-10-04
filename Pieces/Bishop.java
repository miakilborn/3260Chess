package Pieces;
import Game.Coordinate;
public class Bishop extends Piece{
    //this class is intentionally left blank, see Piece
    public Bishop(String colour, Coordinate position){
        super.setPosition(position);
        super.setColour(colour);
    }

    public Bishop(String s){
        if(s.substring(0,"Bishop".length()).equals("Bishop")){
            super.setupFromString("Bishop",s);
        }
    }
}
