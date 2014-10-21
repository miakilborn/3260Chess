package RuleSets.Rules;
import Game.*;
import RuleSets.IRuleSet;
public interface IRule {

    public Result checkAndMakeMove(IBoard board, IRuleSet rules, Move move);
    public Result checkMove(IBoard board, IRuleSet rules, Move move);

}
