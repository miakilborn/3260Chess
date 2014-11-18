package Game;

import Game.*;
import Pieces.*;
import RuleSets.Rules.IRule;

import java.util.ArrayList;

public class Player {
    private static String name;
    private static String colour;
    private int score;

    private View ui;
    private Controller controller;

    /**
     * Construct player with the players game name, and colour
     *
     */
    public Player(Controller controller){
        this.controller = controller;
        ui = new TextView();
        if(controller.isMaster()){
            colour = "White";
        } else {
            colour = "Black";
        }
        ui.setPlayerColour(colour);
        name = ui.getUsername();
    }

    public void printEndGame(Result result){
        this.ui.displayGameOver(result.getMessage());
    }

    /**
    * Main class, runs the program
    *
    *
    */
    public void game(){
        //display board
        
        //get move
        Move move = ui.getMove();

        //attempt to perform the move
        try{
            if (move != null){
                controller.makeMove(move, null);
            }
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public ArrayList<IRule> getRules(){
        //return type to be determined
        return ui.getRules();
    }

    public void printResult(Result result){
        if (result != null){
            System.out.println(result.getMessage());
        }
    }
    /**
    *
    *
    */
    public void updateDisplay(){
        ui.displayBoard(controller.getBoard());
        printResult(controller.getResult());
        System.out.print("Your move: ");
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
