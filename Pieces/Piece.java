import Coordinate;

public abstract class Piece{
    private Coordinate position;
    private String colour;
    private boolean isCaptured = false;
    
    public Piece(){
        
    }
    
    public Piece(String colour, Coordinate position){
        this.setColour(colour);
        this.setPosition(position);
    }
    
    public void capture(){
        this.isCaptured = true;
        this.setPosition(-1,-1);
    }
    
    public void setPosition(Coordinate position) {
        this.position = position;
    }
    
    public void setPosition(int x, int y){
        this.position.setX(x);
        this.position.setY(y);
    }
    
    public Coordinate getPosition(){
        return this.position;
    }
    
    public void setColour(String colour){
        this.colour = colour;
    }
    
    public String getColour(){
        return this.colour;
    }
    
    public boolean isCaptured(){
        return this.isCaptured;
    }
    
}