package RuleSets;
import Game.*;
import Pieces.*;
import RuleSets.Rules.IRule;
import java.util.*;
public interface IRuleSet {
    public boolean checkMovePawn(Board board, Move move);
    public boolean checkMoveRook(Board board, Move move);
    public boolean checkMoveKing(Board board, Move move);
    public boolean checkMoveQueen(Board board, Move move);
    public boolean checkMoveKnight(Board board, Move move);
    public boolean checkMoveBishop(Board board, Move move);


	public void setupBoard(Board board);
	public boolean hasWon(Board board, String colour);

    public Move getLastMove();

    public Result makeMove(Board board, Move move, ArrayList<IRule> additionalRules);
	public Result checkMove(Board board, Move move, ArrayList<IRule> additionalRules);

}
