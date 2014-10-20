package RuleSets.Rules;
import Game.*;
import RuleSets.*;
import Pieces.*;
public class EnPassent implements IRule {

    public EnPassent(){

    }
    public boolean checkAndMakeMove(IBoard board, IRuleSet rules, Move move){
        return false;
    }
    public Result checkMove(IBoard board, IRuleSet rules, Move move){
        return null;
    }
}
