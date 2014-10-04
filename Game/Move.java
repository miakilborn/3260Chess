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

	public Move(String s){
		//Need a basic string constructor... this will rely on a coordinate
		//toString and a coordinate string constructor as well.
		piece = null;
		nextPosition = null;

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

	public String tostring(){
		//Should return a string that you can use in the constructor to rebuild the object.
		return null;
	}

}
