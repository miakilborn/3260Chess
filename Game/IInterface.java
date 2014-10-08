package Game;
import Game.Move;
/*
getMove
eventBoard

raise an action (if we can) for the user's move
*/
public interface IInterface {
	public String getUsername();
	public void displayBoard(IBoard board);
	public Move getMove(IBoard board, String colour, String name);
	public void displayMessage(String msg);
}