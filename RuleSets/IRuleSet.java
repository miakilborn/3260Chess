package RuleSets;
import java.util.*;
import Game.*;
import Pieces.*;
public interface IRuleSet {

	public void setupBoard(IBoard board);

	public boolean checkMove(IBoard board, Move move);

}
