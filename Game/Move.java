package Game;
import Pieces.*;
import Game.*;

public class Move {
    private boolean valid = false;
    private final Coordinate currentPosition;
    private final Coordinate nextPosition;
    private boolean isDraw = false;

    private Coordinate captured = null;

    public Move(Coordinate currentPosition, Coordinate nextPosition){
            this.currentPosition = currentPosition;
            this.nextPosition = nextPosition;
            this.captured=null;
    }

    public Move(String s){
        if(s.equals("Draw")){
            isDraw = true;
            this.currentPosition = new Coordinate(-1,-1);
            this.nextPosition = new Coordinate(-1,-1);
        }
        else if(s.charAt(0) == 'M'){
                s = s.replaceAll("M\\(|\\)$","");
                String c = s.substring(0,s.lastIndexOf("),")+1);
                s = s.replace(c+",","");

                String c1 = c.substring(0,c.lastIndexOf("),")+1);
                c = c.replace(c1+",","");

                currentPosition = new Coordinate(c1);
                nextPosition = new Coordinate(c);
                if(s.equals("true")){
                        this.captured = new Coordinate(c);
                }
        }
        else{
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

    public void setPieceCaptured(Coordinate piece){
            captured = piece;
    }

    public void setValid(boolean valid){
            this.valid = valid;
    }

    public String toString(){
            //Should return a string that you can use in the constructor to rebuild the object.
            return ("M("+currentPosition.toString()+","+nextPosition.toString()+","+(captured==null?"false":"true")+")");
    }
}
