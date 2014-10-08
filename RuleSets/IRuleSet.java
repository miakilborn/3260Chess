package RuleSets;
import java.util.*;
import Game.*;
import Pieces.*;
public interface IRuleSet {

	public void setupBoard(IBoard board);
	public boolean hasWon(IBoard board, String colour);
	public boolean checkMove(IBoard board, Move move);
	public boolean isInCheckMate(IBoard board, String colour);
	public boolean isInCheck(IBoard board, String colour);

}
