package Pieces;

import Game.Coordinate;

public abstract class Piece{
    private Coordinate position;
    private String colour;
    private boolean isCaptured = false;
    private boolean hasMoved = false;

    public void capture(){
        this.isCaptured = true;
        this.position = null;
    }

    public void setPosition(Coordinate position) {
        this.position = position;
    }

    public Coordinate getPosition(){
        return this.position;
    }

    public void setColour(String colour){
        this.colour = colour;
    }

    public void setHasMoved(boolean state){
        this.hasMoved = state;
    }

    public String getColour(){
        return this.colour;
    }

    public boolean isCaptured(){
        return this.isCaptured;
    }

    public String toString(){
        return this.getClass().toString().replace("class Pieces.","")+"("+position.toString()+","+colour+","+isCaptured+")";
    }

    public void setupFromString(String piece,String s){
        s = s.replaceAll(piece+"\\(|\\)$","");
        String coordString = s.substring(0,s.lastIndexOf(")")+1);
        s = s.replace(coordString,"");
        String[] sp = s.split(",");
        this.setPosition(new Coordinate(coordString));
        this.setColour(sp[1]);
        if(Boolean.getBoolean(sp[2])){
            this.capture();
        }
    }
}
