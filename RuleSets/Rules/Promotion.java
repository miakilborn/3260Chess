package RuleSets.Rules;
import Game.*;
import RuleSets.*;
import Pieces.*;
import java.util.Scanner;
public class Promotion implements IRule {

    public Promotion(){

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
    
    public boolean checkAndMakeMove(IBoard board, IRuleSet rules, Move move){
        Piece newPiece = null;
        if (checkMove(board, rules, move).isValid()){
            Piece piece = board.getPieceFromPosition(move.getCurrentPosition());
            Piece cap = board.getPieceFromPosition(move.getNextPosition());
            if(cap != null){
                System.err.println("REMOVING: "+cap.toString());
                board.removePiece(cap);
            }
            
            newPiece = promptPromotion(piece.getColour());
            newPiece.setPosition(move.getNextPosition());
            board.removePiece(piece);
            board.addPiece(newPiece);
            return true;
        }
        return false;
    }

    public Result checkMove(IBoard board, IRuleSet rules, Move move){
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
        if (rules.checkMovePawn(board, move)){
            return new Result(true);
        } else {
            return new Result(false, "Generic invalid move");
        }
    }
}