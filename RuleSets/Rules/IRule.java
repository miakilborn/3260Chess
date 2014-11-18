package RuleSets.Rules;
import Game.*;
import RuleSets.IRuleSet;
public interface IRule {

    public Result checkAndMakeMove(Board board, IRuleSet rules, Move move);
    public Result checkMove(Board board, IRuleSet rules, Move move);

}
