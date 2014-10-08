package Pieces;
import Game.Coordinate;
public class Knight extends Piece{
    //this class is intentionally left blank, see Piece
    public Knight(String colour, Coordinate position){
        super.setPosition(position);
        super.setColour(colour);
    }

    public Knight(String s){
        if(s.substring(0,"Knight".length()).equals("Knight")){
            super.setupFromString("Knight",s);
        }
    }

    @Override
    public char toChar(){
        return 'n';
    }
}
