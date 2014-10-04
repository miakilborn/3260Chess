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
            s = s.replaceAll("Queen\\(|\\)$","");
            String coordString = s.substring(0,s.lastIndexOf(")")+1);
            s = s.replace(coordString,"");
            String[] sp = s.split(",");
            super.setPosition(new Coordinate(coordString));
            super.setColour(sp[1]);
            if(Boolean.getBoolean(sp[2])){
                super.capture();
            }
        }
    }
}
