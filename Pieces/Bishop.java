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
            s = s.replaceAll("Bishop\\(|\\)$","");
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
