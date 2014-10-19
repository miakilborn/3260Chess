package Game;
import Game.Move;
/*
getMove
eventBoard

raise an action (if we can) for the user's move
*/
public interface IInterface {
	public Move getMove(IBoard board);
	public void displayBoard(IBoard board);
	public void setPlayerColour(String colour);
	public String getUsername();
	public void displayMessage(String msg);
}