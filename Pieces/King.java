package Pieces;
import Game.Coordinate;
public class King extends Piece{
    //this class is intentionally left blank, see Piece
    public King(String colour, Coordinate position){
        super.setPosition(position);
        super.setColour(colour);
    }

    public King(String s){
        if(s.substring(0,"King".length()).equals("King")){
            super.setupFromString("King",s);
        }
    }

    @Override
    public char toChar(){
        return 'k';
    }
}
