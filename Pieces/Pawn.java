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
            s = s.replaceAll("Pawn\\(|\\)$","");
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
