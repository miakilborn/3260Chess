package Game;
import Pieces.*;
import Game.*;

public class Move {
    private boolean valid = false;
    private final Coordinate currentPosition;
    private final Coordinate nextPosition;
    private boolean isDraw = false;
    private boolean performed = false; //defines if move was performed already or not
    private String data = "";
    private String colour = ""; //colour of player that made this move

    private Coordinate captured = null;

    public Move(String colour, Coordinate currentPosition, Coordinate nextPosition){
            this.currentPosition = currentPosition;
            this.nextPosition = nextPosition;
            this.captured = null;
            this.colour = colour;
    }

    public Move(String s){
        System.err.println("Constructing: " + s);
        this.colour = colour;
        s = s.replaceAll("M\\(|\\)$","");
        String[] tokens = s.split("\\|");
        if (tokens.length >= 4){
            
            String c1 = tokens[0];
            String c2 = tokens[1];
            System.out.println("c1: " + c1);
            System.out.println("c2: " + c2);
            
            currentPosition = new Coordinate(c1);
            nextPosition = new Coordinate(c2);
            if(tokens[2].equals("true")){
                this.captured = new Coordinate(c2);
            }

            if (tokens[3].equals("true")){
                this.isDraw = true;
                System.err.println("This move is constructed with isDraw flag set TRUE");
            }
            if (tokens.length >= 5){
                this.colour = tokens[4];
            } 
        } else if(tokens.length >= 2){
            if (tokens[0].equalsIgnoreCase("Draw"))
                isDraw = true;
            this.data = tokens[0];
            this.colour = tokens[1];
            this.currentPosition = null;
            this.nextPosition = null;
        }else {
            System.err.println("Invalid move object, cannot construct properly: " + s);
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
    
    public String getData(){
        return data;
    }
    
    public boolean getMoved(){
            return performed;
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
    
    public void setMoved(boolean performed){
            this.performed = performed;
    }
    
    /**
     * Temporary method for overriding this variable, for special cases to allow
     * things promotion to maintain players turn when selecting piece type
     * @param colour 
     */
    public void setColour(String colour){
        this.colour = colour;
    }

    public String toString(){
            //Should return a string that you can use in the constructor to rebuild the object.
        if (currentPosition == null || nextPosition == null){
            return ("M("+data+"|"+colour+")");
        } else {
            return ("M("+currentPosition.toString()+"|"+nextPosition.toString()
                +"|"+(captured==null?"false":"true")+"|"+Boolean.toString(isDraw)+"|"+colour+")");
        }
        
    }
}
