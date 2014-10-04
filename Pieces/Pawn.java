package Pieces;
import Game.Coordinate;
public class Pawn extends Piece{
    //this class is intentionally left blank, see Piece
    public Pawn(String colour, Coordinate position){
        super.setPosition(position);
        super.setColour(colour);
    }

    public Pawn(String s){
        if(s.substring(0,"Pawn".length()).equals("Pawn")){
            super.setupFromString("Pawn",s);
        }
    }
}
