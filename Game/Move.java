package Game;
import Pieces.*;
import Game.*;
public class Move {
	private boolean valid;
	private final Piece piece;
	private final Coordinate nextPosition;

	private Piece captured;

	public Move(Piece piece, Coordinate nextPosition){
		this.piece = piece;
		this.nextPosition = nextPosition;
	}

	public boolean isValid(){
		return valid;
	}

	public Coordinate getCurrentPosition(){
		if (piece != null)
			return piece.getPosition();
		else
			return null;
	}

	public Coordinate getNextPosition(){
		return nextPosition;
	}

	public Piece getPieceCaptured(){
		return captured;
	}

	public Piece getPiece(){
		return this.piece;
	}

	public void setPieceCaptured(Piece piece){
		captured = piece;
	}

	public void setValid(boolean valid){
		this.valid = valid;
	}



}
