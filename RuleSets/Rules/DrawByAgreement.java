package RuleSets.Rules;
import Game.*;
import RuleSets.*;
import Pieces.*;
import java.util.Scanner;
public class DrawByAgreement implements IRule {

    /* 
        When draw by agreement is active,
        player may enter "draw" instead of a move
        to suggest a mutual draw. If the other user then
        also enters draw, the game ends in a tie.
        
        Move will contain a isDraw(), if move has isDraw() true, return a result with Message draw, validness of true?
    */

    public DrawByAgreement(){

    }




    public boolean checkAndMakeMove(IBoard board, IRuleSet rules, Move move){
        
        return true;
    }

    public Result checkMove(IBoard board, IRuleSet rules, Move move){
        
        return new Result(true, "Draw");
        
    }
}
