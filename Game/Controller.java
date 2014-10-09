package Game;
import java.util.*;
import java.net.*;
import java.io.*;
import RuleSets.*;
import Pieces.*;

public class Controller{
    private static IRuleSet rules;
    private static IBoard board;

    public static void main(String []args) throws InterruptedException{
        Move currMove = null;
        boolean isValid = false;
        Player white;
        Player black;   
        String name;
        TextInterface ui = new TextInterface();

        white = new Player("White");
        black = new Player("Black");

        board = new Standard8x8Board();
        rules = new Standard8x8();
        rules.setupBoard(board);

        name = ui.getUsername();
        white.setName(name);
        ui.displayMessage(name + " is the White player. Pieces will be in UPPER CASE");

        name = ui.getUsername();
        black.setName(name);
        ui.displayMessage(name + " is the Black player. Pieces will be in lower case");

        ui.displayMessage("Starting Chess.");

        ui.displayBoard(board);

        //white goes first
        while(true){
            currMove = ui.getMove(board, white.getColour(), white.getName());
            while(currMove == null){
                currMove = ui.getMove(board, white.getColour(), white.getName());
            }

            isValid = rules.checkMove(board,currMove);
            while(!isValid){
                ui.displayMessage("Invalid move. Try again.");
            }
            board.makeMove(currMove);

            //refresh the board
            ui.displayBoard(board);

            //check for winning
            if(rules.isInCheckMate(board, black.getColour())){
                ui.displayMessage("White Wins! Game Over.");
                return;
            } else if (rules.isInCheck(board, black.getColour())){
                ui.displayMessage("Black is in Check, game over for now.");
                return;
            }

            currMove = ui.getMove(board, black.getColour(), black.getName());
            while(currMove == null){
                currMove = ui.getMove(board, black.getColour(), black.getName());
            }

            isValid = rules.checkMove(board,currMove);
            while(!isValid){
                ui.displayMessage("Invalid move. Try again.");
            }
            board.makeMove(currMove);

            //refresh the board
            ui.displayBoard(board);

            //check for winning
            if(rules.isInCheckMate(board, black.getColour())){
                ui.displayMessage("Black Wins! Game Over.");
                return;
            } else if (rules.isInCheck(board, black.getColour())){
                ui.displayMessage("White is in Check, game over for now.");
                return;
            }
        }
    }
}
