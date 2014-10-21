package RuleSets;
import Game.*;
import Pieces.*;
import RuleSets.Rules.IRule;
import java.util.*;
public interface IRuleSet {
        public boolean checkMovePawn(IBoard board, Move move);
        public boolean checkMoveRook(IBoard board, Move move);
        public boolean checkMoveKing(IBoard board, Move move);
        public boolean checkMoveQueen(IBoard board, Move move);
        public boolean checkMoveKnight(IBoard board, Move move);
        public boolean checkMoveBishop(IBoard board, Move move);
    
    
	public void setupBoard(IBoard board);
	public boolean hasWon(IBoard board, String colour);
        
        public Result makeMove(IBoard board, Move move, ArrayList<IRule> additionalRules);
	public Result checkMove(IBoard board, Move move, ArrayList<IRule> additionalRules);

}
