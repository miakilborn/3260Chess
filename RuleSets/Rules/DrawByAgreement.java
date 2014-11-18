package RuleSets.Rules;
import Game.*;
import RuleSets.*;
import Pieces.*;
import java.util.Scanner;
public class DrawByAgreement implements IRule {

    public DrawByAgreement(){

    }



    public Result checkAndMakeMove(Board board, IRuleSet rules, Move move){
        
        return checkMove(board, rules, move);
        
    }

    public Result checkMove(Board board, IRuleSet rules, Move move){
        if (move.isDraw() && rules.getLastMove() != null && rules.getLastMove().isDraw()) {
            System.err.println(rules.getLastMove().toString());
            return new Result(true, true, "Confirmed draw");
        } else if (move.isDraw()){
            return new Result(true, false, "Draw");
        }
        return new Result(false);
    }
}
