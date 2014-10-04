import Coordinate;

public class Pawn extends Piece {
    public Pawn(){
        //empty constructor
    }
    
    public Pawn(String colour, Coordinate position){
        super.setColour(colour);
        super.setPosition(position);
    }

    public void capture(){
        super.setPosition(-1, -1);
    }
}
