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




    public Result checkAndMakeMove(IBoard board, IRuleSet rules, Move move){
        return new Result(false);
    }

    public Result checkMove(IBoard board, IRuleSet rules, Move move){
        
        if (move.isDraw())
        {
            return new Result(true, "Draw");
        }
        else
        {
            return new Result(false, "Wasn't Draw");
        }

        
    }
}
