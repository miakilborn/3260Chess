package Rules;

import Game.*;
import Pieces.*;
import java.util.Scanner;

/**
 *
 * @author Tim
 */
public class Promotion extends RulesDecorator {
    private Rules rules;
    
    public Promotion(Rules rules){
        this.rules = rules;
        this.board = rules.board;
    }
    
    /*
        Checks that the position the piece wishes to move is a
        promotional tile
    */
    private boolean checkPosition(String colour, Coordinate nextPosition){
        if (colour.equals("White")){
            if (nextPosition.getY() != 8){
                return false;
            }
        } else if (colour.equals("Black")){
            if (nextPosition.getY() != 1){
                return false;
            }
        }
        return true;
    }
    
    /**
     * Obtain the piece that the player wants to promote to
     * @param colour
     * @return a new Piece object
     */
    private Piece promptPromotion(String colour){
        Piece piece = null;
        Scanner keyboard = new Scanner(System.in);
        System.out.println("What would you like to be promoted to? Options [q,n,r,b]");
        while (piece == null){
            System.out.print("$> ");
            String pieceStr = keyboard.nextLine();
            char p = pieceStr.toLowerCase().charAt(0);
            switch(p){
                case 'q':
                    piece = new Queen(colour, new Coordinate(-1,-1));
                    break;
                case 'n':
                    piece = new Knight(colour, new Coordinate(-1,-1));
                    break;
                case 'r':
                    piece = new Rook(colour, new Coordinate(-1,-1));
                    break;
                case 'b':
                    piece = new Bishop(colour, new Coordinate(-1,-1));
                    break;
                default:
                    System.out.println("Invalid choice, try again");
                    break;
            }
        }
        return piece;
    }
    
    /**
     * Performs all checks to see if move if valid before making the move
     * @param move
     * @return validity of the move for this rule
     */
    private Result checkMove(Move move){
        Piece piece = board.getPieceFromPosition(move.getCurrentPosition());
        
        //Verify is a pawn
        if (!(piece instanceof Pawn)){
            return new Result(false, "Piece is not a pawn");
        }
        
        //Verify movement position is a promotional position
        if (!checkPosition(piece.getColour(), move.getNextPosition())){
            return new Result(false, "Not a promotional position");
        }
        
        //Check if move is a valid move
        Result result = rules.checkMovePawn(move);
        if (result.isValid()){
            return result;
        } else {
            return result;
        }
    }

    @Override
    public Result makeMove(Move move) {
        Result result = checkMove(move); //Do promotion checks
        if (!move.getMoved() && result.isValid()){ //perform the move
            move.setPieceCaptured(move.getNextPosition());
            Piece p = this.promptPromotion(move.getColour());
            p.setPosition(move.getNextPosition());
            board.removePiece(board.getPieceFromPosition(move.getCurrentPosition()));
            move.setMoved(true);
            rules.makeMove(move);
        } else { //this rule isn't applicable, try another rule
            result = rules.makeMove(move);
        }
        return result;
    }
    
    
}
