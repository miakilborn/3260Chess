package RuleSets.Rules;
import Game.*;
import RuleSets.IRuleSet;
public interface IRule {

    public boolean checkAndMakeMove(IBoard board, IRuleSet rules, Move move);
    public Result checkMove(IBoard board, IRuleSet rules, Move move);

}
