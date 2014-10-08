package Game;

import Game.*;
import Pieces.*;

public class Player {
    private String name;
    private String colour;
    private String whatCase;
    private int score;

 
    private Controller controller;

    /**
     * Construct player with the players game name, and colour
     *
     */
    public Player(String colour){
        this.colour = colour;
    }

    /**
     * Get the players game name
     *
     */
    public String getPlayerName(){
        return name;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    /**
     * Get the players game colour
     *
     */
    public String getColour(){
        return colour;
    }

    /**
     * Get the players current game score
     *
     */
    public int getScore(){
        return score;
    }
}
