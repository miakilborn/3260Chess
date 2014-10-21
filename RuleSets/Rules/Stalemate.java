package RuleSets.Rules;
import Game.*;
import RuleSets.*;
import Pieces.*;
import java.util.Scanner;
public class Stalemate implements IRule {
    
    /* 
    
        After moving a piece, other player is checked to see if they are in stalemate.
        
        Board must cycle through all the players pieces, finding all valid moves,
        then for each move, check each of their opponents pieces and see if one of the opponents
        pieces would have the king in check.
        
        If for all possible moves by the player, the result would be them being in check,
        the game is in Stalemate.
    
    */

    public Stalemate(){

    }

    
    public Result checkAndMakeMove(IBoard board, IRuleSet rules, Move move){
        
        return new Result(false);
    }

    public Result checkMove(IBoard board, IRuleSet rules, Move move){
        
        return new Result(false, "Stalemate");
    }
}
