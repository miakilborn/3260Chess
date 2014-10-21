package Game;
import Game.Move;
import RuleSets.Rules.IRule;

import java.util.ArrayList;

/*
getMove
eventBoard

raise an action (if we can) for the user's move
*/
public interface IInterface {
	public Move getMove();
	public void displayBoard(IBoard board);
	public void setPlayerColour(String colour);
	public String getUsername();
	public void displayMessage(String msg);
    public void displayGameOver(String reason);
    public ArrayList<IRule> getRules();
}