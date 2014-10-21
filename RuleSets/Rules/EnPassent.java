package RuleSets.Rules;
import Game.*;
import RuleSets.*;
import Pieces.*;
public class EnPassent implements IRule {

    public EnPassent(){

    }
    public Result checkAndMakeMove(IBoard board, IRuleSet rules, Move move){
        return new Result(false);
    }
    public Result checkMove(IBoard board, IRuleSet rules, Move move){
        return new Result(false);
    }
}
