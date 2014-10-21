package RuleSets.Rules;
import Game.*;
import RuleSets.*;
import Pieces.*;
import java.util.Scanner;
public class DrawByAgreement implements IRule {

    public DrawByAgreement(){

    }



    public Result checkAndMakeMove(IBoard board, IRuleSet rules, Move move){
        
        Result result = checkMove(board, rules, move);
    
        return result;
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
