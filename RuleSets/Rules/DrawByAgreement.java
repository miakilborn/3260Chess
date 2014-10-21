package RuleSets.Rules;
import Game.*;
import RuleSets.*;
import Pieces.*;
import java.util.Scanner;
public class DrawByAgreement implements IRule {

    public DrawByAgreement(){

    }



    public Result checkAndMakeMove(IBoard board, IRuleSet rules, Move move){
        
        return checkMove(board, rules, move);
        
    }

    public Result checkMove(IBoard board, IRuleSet rules, Move move){
        if (move.isDraw() && rules.getLastMove() != null && rules.getLastMove().isDraw())
        {
            return new Result(true, true, "Draw");
        }
        return new Result(true, "Draw");
    }
}
