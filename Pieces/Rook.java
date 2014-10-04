package Pieces;
import Game.Coordinate;
public class Rook extends Piece{
    //this class is intentionally left blank, see Piece
    public Rook(String colour, Coordinate position){
        super.setPosition(position);
        super.setColour(colour);
    }

    public Rook(String s){
        if(s.substring(0,"Rook".length()).equals("Rook")){
            super.setupFromString("Rook",s);
        }
    }
}
