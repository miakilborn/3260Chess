package Client;
import Game.*;
import Pieces.*;
import Rules.*;

import java.util.ArrayList;
import java.util.Scanner;

public class TextView implements View {
    private String moveString;
    private final String inputPattern = "([A-Ha-h][1-8])\\-([A-Ha-h][1-8])";
    private String name;
    private String colour;
    private String whatCase;
    public Boolean update;
    Scanner keyboard = new Scanner(System.in);
    Board board;

    public String getUsername(){
        System.out.print("Name: ");
        name = keyboard.nextLine();
        return name;
    }

    public void displayBoard(Board board){
        //clear the screen
        final String ANSI_CLS = "\u001b[2J";
        final String ANSI_HOME = "\u001b[H";
        System.out.print(ANSI_CLS + ANSI_HOME);
        System.out.flush();

        System.out.println("Welcome to Chess, " + name + ". You are " + colour + ", which is displayed as " + whatCase + " case.");
        System.out.println("Moves are formatted as \"XY-XY\" ex \"B2-B4\"");
        System.out.println("To request a draw by agreement (if enabled) enter 'Draw' ");

        this.board = board;
        printBoard();
    }

    private void printBoard(){
       // Pawn(C(1,3), White, false)
        ArrayList<Piece> pieces = board.getPieces();
        char[][] boardStr = new char[8+1][8+1];

        for (int x=0;x<8+1;x++)
            for (int y=0;y<8+1;y++)
                boardStr[x][y] = ' ';

        for (int i=0;i<pieces.size();i++){
            Piece piece = pieces.get(i);
            String pieceStr = piece.toString();
            String coordStr = pieceStr.substring(pieceStr.lastIndexOf("C("),pieceStr.lastIndexOf("),")+1);
            Coordinate coord = new Coordinate(coordStr);

            if (piece.getColour().equals("White"))
                boardStr[coord.getX()][coord.getY()] = Character.toUpperCase(piece.toChar());
            else
                boardStr[coord.getX()][coord.getY()] = Character.toLowerCase(piece.toChar());

        }

        // Print board to stdout

        System.out.print("     ");
        for (char c='A';c<='H';c++)
            System.out.print(c + " ");
        System.out.println("\n" + "  |------------------ ");


        for (int y=boardStr.length-1;y>=1;y--){
            System.out.print(y + " |  ");
            for (int x=1;x<boardStr[y].length;x++){
                System.out.print(boardStr[x][y] + " ");
            }
            System.out.println();
        }

        System.out.print("  |------------------ \n     ");
        for (char c='A';c<='H';c++)
            System.out.print(c + " ");

        System.out.println();
    }

    public Move getMove(){
        moveString = keyboard.nextLine();
        if((moveString.equals("Draw")) ||(moveString.equals("draw")) || (moveString.equals("DRAW"))){
            Move move = new Move("Draw"+","+colour);
            return move;
        }

        while(!moveString.matches(inputPattern)){
            System.out.println("Incorrect format of input. See guidelines at top of window.");
            System.out.print("Your move: ");
            moveString = keyboard.nextLine();
        }

        int x1 = (int)moveString.toUpperCase().charAt(0)-64;
        int y1 = (int)moveString.toUpperCase().charAt(1)-48;
        int x2 = (int)moveString.toUpperCase().charAt(3)-64;
        int y2 = (int)moveString.toUpperCase().charAt(4)-48;

        Piece p = board.getPieceFromPosition(new Coordinate(x1,y1));
        Move move = null;
        if (p != null){
            move = new Move(p.getColour(), new Coordinate(x1,y1), new Coordinate(x2,y2));

            if(!p.getColour().equals(this.colour)){
                System.out.println("The selected piece does not belong to you. Please try again.");
                System.err.println("The selected piece does not belong to you. Please try again.");
                move = null;
            }
        } else {
            System.out.println("Please select a piece.");
            System.out.print("Your move: ");
        }
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

    public String question(String q){
        System.out.println("Question: "+q);
        return keyboard.nextLine();
    }

    public void displayMessage(String msg){
            System.out.println(msg);
            System.err.println(msg);
    }

    public void displayGameOver(String reason){
        //clear the screen
        final String ANSI_CLS = "\u001b[2J";
        final String ANSI_HOME = "\u001b[H";
        System.out.print(ANSI_CLS + ANSI_HOME);
        System.out.flush();

        System.out.println("--- GAME OVER ---");
        System.out.println(reason);
        System.out.println("--- Thanks for playing. ---");
    }

    public Rules decorateRules(Rules rules){
        String option;

        System.out.println("Hello, " + name + ". You are the master for this session, please select your rules.");
        System.out.print("Promotion (Y/N): ");
        option = keyboard.nextLine();
        if((option.charAt(0) == 'Y') || (option.charAt(0) =='y')){
            rules = new Promotion(rules);
        }

//        System.out.print("Castling (Y/N): ");
//        option = keyboard.nextLine();
//        if((option.charAt(0) == 'Y') || (option.charAt(0) =='y')){
//            Castling c = new Castling();
//            rules.add(c);
//            none = false;
//        }
//
//        System.out.print("En Passent (Y/N): ");
//        option = keyboard.nextLine();
//        if((option.charAt(0) == 'Y') || (option.charAt(0) =='y')){
//            EnPassent e = new EnPassent();
//            rules.add(e);
//            none = false;
//        }
//
//        System.out.print("Draw by agreement (Y/N): ");
//        option = keyboard.nextLine();
//        if((option.charAt(0) == 'Y') || (option.charAt(0) =='y')){
//            DrawByAgreement d = new DrawByAgreement();
//            rules.add(d);
//            none = false;
//        }
//
//        System.out.print("Stalemate (Y/N): ");
//        option = keyboard.nextLine();
//        if((option.charAt(0) == 'Y') || (option.charAt(0) =='y')){
//            Stalemate s = new Stalemate();
//            rules.add(s);
//            none = false;
//        }
//
//        System.out.print("50 move rule (Y/N): ");
//        option = keyboard.nextLine();
//        if((option.charAt(0) == 'Y') || (option.charAt(0) =='y')){
//            FiftyMoveRule f = new FiftyMoveRule();
//            rules.add(f);
//            none = false;
//        }

        return rules;
    }
}
