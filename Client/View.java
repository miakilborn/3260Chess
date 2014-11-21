package Client;
import Game.Board;
import Game.Move;
import Rules.*;

/*
getMove
eventBoard

raise an action (if we can) for the user's move
*/
public interface View {
    public Move getMove();
    public void displayBoard(Board board);
    public void setPlayerColour(String colour);
    public String getUsername();
    public void displayMessage(String msg);
    public void displayGameOver(String reason);
    public Rules decorateRules(Rules rule);
}