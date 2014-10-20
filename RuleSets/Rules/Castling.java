package RuleSets.Rules;
import Game.*;
import RuleSets.*;
import Pieces.*;
public class Castling implements IRule {

    public Castling(){

    }
    public boolean checkAndMakeMove(IBoard board, IRuleSet rules, Move move){
        return false;
    }
    public Result checkMove(IBoard board, IRuleSet rules, Move move){
        return null;
    }
}
