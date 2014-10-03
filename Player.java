



public class Player {
    private final String name;
    private final String colour;
    private int score;
    
    public Player(String name, String colour){
        this.name = name;
        this.colour = colour;
    }
    
    public String getPlayerName(){
        return name;
    }
    
    public String getColour(){
        return colour;
    }
    
    public int getScore(){
        return score;
    }
}