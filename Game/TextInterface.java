package Game;
import Game.*;
import Pieces.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class TextInterface implements IInterface {
	private String moveString;
	private final String inputPattern = "\\([1-8],[1-8]\\)-\\([1-8],[1-8]\\)";
	private String name;
	private String colour;
	private String whatCase;
	Scanner keyboard = new Scanner(System.in);

	public String getUsername(){
        System.out.print("Name: ");
        name = keyboard.nextLine();
        return name;
	}

	public void displayBoard(IBoard board){
        //clear the screen
        final String ANSI_CLS = "\u001b[2J";
        final String ANSI_HOME = "\u001b[H";
        System.out.print(ANSI_CLS + ANSI_HOME);
        System.out.flush();

        System.out.println("Welcome to Chess, " + name + ". You are " + colour + ", which is displayed as " + whatCase + " case.");
        System.out.println("Moves are formatted as \"(sourceX,sourceY)-(destinationX,destinationY)\" ex \"(2,2)-(4,4)\"");

        printBoard(board);
	}

    public void printBoard(IBoard board){
       // Pawn(C(1,3), White, false)
        ArrayList<Piece> pieces = board.getPieces();
        char[][] boardStr = new char[8+1][8+1];

        for (int i=0;i<pieces.size();i++){
            Piece piece = pieces.get(i);
            String pieceStr = piece.toString();
            String coordStr = pieceStr.substring(pieceStr.lastIndexOf("C("),pieceStr.lastIndexOf("),")+1);
            Coordinate coord = new Coordinate(coordStr);

            boardStr[coord.getX()][coord.getY()] = Character.toUpperCase(piece.toChar());

        }

        // Print board to stdout
        for (int y=1;y<boardStr.length;y++){
            for (int x=1;x<boardStr[y].length;x++)
                System.out.print(boardStr[x][y]);
            System.out.println();
        }


    }

	public Move getMove(IBoard board){
        System.out.print("Your move: ");
        moveString = keyboard.nextLine();

        while(!moveString.matches(inputPattern)){
            System.out.println("Incorrect format of input. See guidelines at top of window.");
            System.out.print("Your move: ");
            moveString = keyboard.nextLine();
        }

        System.out.println("Verifying...");

        //parse input into a Move class instance
        StringTokenizer tokenizer = new StringTokenizer(moveString, "-");
        String oldPosition = "C"+tokenizer.nextElement();
        String newPosition = "C"+tokenizer.nextElement();

        Move move = new Move(new Coordinate(oldPosition), new Coordinate(newPosition));
        return move;
	}

	public void setPlayerColour(String colour){
		this.colour = colour;
		if(colour.equals("White")){
			this.whatCase = "upper";
		} else {
			this.whatCase = "lower";
		}
	}

	public void displayMessage(String msg){
		System.out.println(msg);
	}

}