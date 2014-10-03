



public class Player {
    private final String name;
    private final String colour;
    private int score;
    
    /**
     * Construct player with the players game name, and colour
     * 
     */
    public Player(String name, String colour){
        this.name = name;
        this.colour = colour;
    }
    
    /**
     * Get the players game name
     * 
     */
    public String getPlayerName(){
        return name;
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
