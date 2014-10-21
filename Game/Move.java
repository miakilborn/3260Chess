package Game;
import Pieces.*;
import Game.*;

public class Move {
    private boolean valid = false;
    private final Coordinate currentPosition;
    private final Coordinate nextPosition;
    private boolean isDraw = false;
    private String colour = ""; //colour of player that made this move

    private Coordinate captured = null;

    public Move(String colour, Coordinate currentPosition, Coordinate nextPosition){
            this.currentPosition = currentPosition;
            this.nextPosition = nextPosition;
            this.captured=null;
            this.colour = colour;
    }

    public Move(String s){
        this.colour = colour;
        String[] tokens = s.split(",");
        if(tokens.length == 2 && tokens[0].equals("Draw")){
            isDraw = true;
            this.colour = tokens[1];
            this.currentPosition = new Coordinate(-1,-1);
            this.nextPosition = new Coordinate(-1,-1);
        }
        else if(s.charAt(0) == 'M'){
                System.err.println(s);
                s = s.replaceAll("M\\(|\\)$","");
                String c = s.substring(0,s.lastIndexOf("),")+1);
                s = s.replace(c+",","");

                String c1 = c.substring(0,c.lastIndexOf("),")+1);
                c = c.replace(c1+",","");
                
                tokens = s.split(",");
                
                currentPosition = new Coordinate(c1);
                nextPosition = new Coordinate(c);
                if(tokens[0].equals("true")){
                        this.captured = new Coordinate(c);
                }
                
                if (tokens[1].equals("true")){
                    this.isDraw = true;
                    System.err.println("This move is constructed with isDraw flag set TRUE");
                }
                this.colour = tokens[2];
        }
        else {
                currentPosition = null;
                nextPosition = null;
                captured = null;
        }
    }

    public boolean isDraw(){
        return isDraw;
    }
    
    public boolean isValid(){
            return valid;
    }

    public Coordinate getCurrentPosition(){
            return currentPosition;
    }

    public Coordinate getNextPosition(){
            return nextPosition;
    }

    public Coordinate getPieceCaptured(){
            return captured;
    }
    
    public String getColour(){
        return colour;
    }

    public void setPieceCaptured(Coordinate piece){
            captured = piece;
    }

    public void setValid(boolean valid){
            this.valid = valid;
    }

    public String toString(){
            //Should return a string that you can use in the constructor to rebuild the object.
            return ("M("+currentPosition.toString()+","+nextPosition.toString()+","+(captured==null?"false":"true")+","+Boolean.toString(isDraw)+","+colour+")");
    }
}
