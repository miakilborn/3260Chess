package Game;
import Game.*;
import Pieces.*;
import java.util.Scanner;
import java.util.StringTokenizer;

public class TextInterface implements IInterface {
	private String moveString;
	private final String inputPattern = "([A-Ha-h][1-8])\\-([A-Ha-h][1-8])";
	private String name;
	private String colour;
	private String whatCase;
	Scanner keyboard = new Scanner(System.in);

	public String getUsername(){
        System.out.print("Name: ");
        name = keyboard.nextLine();
        return name;
	}

	public void displayBoard(String boardStr){
        //clear the screen
        final String ANSI_CLS = "\u001b[2J";
        final String ANSI_HOME = "\u001b[H";
        System.out.print(ANSI_CLS + ANSI_HOME);
        System.out.flush();

        System.out.println("Welcome to Chess, " + name + ". You are " + colour + ", which is displayed as " + whatCase + " case.");
        System.out.println("Moves are formatted as \"XY-XY\" ex \"B2-B4\"");

        System.out.print(boardStr);
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

        int x1 = (int)moveString.toUpperCase().charAt(0)-65;
        int y1 = (int)moveString.toUpperCase().charAt(1)-65;
        int x2 = (int)moveString.toUpperCase().charAt(3)-65;
        int y2 = (int)moveString.toUpperCase().charAt(4)-65;

        Move move = new Move(new Coordinate(x1,y1), new Coordinate(x2,y2));
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
