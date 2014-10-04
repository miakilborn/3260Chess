package Pieces;

import Game.Coordinate;

public abstract class Piece{
    private Coordinate position;
    private String colour;
    private boolean isCaptured = false;

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

    public String getColour(){
        return this.colour;
    }

    public boolean isCaptured(){
        return this.isCaptured;
    }

}
