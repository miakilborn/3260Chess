public class Move {
	private boolean valid;
	private final Piece piece;
	private final Coordinate nextPosition;

	private final Piece captured;

	public Move(Piece piece, Coordinate nextPosition){
		this.piece = piece;
		this.nextPosition = nextPosition;
	}

	public void setPieceCaptured(Piece piece){
		captured = piece;
	}

	public boolean isValid(){
		return valid;
	}

	public void setValid(boolean valid){
		this.valid = valid;
	}

	public Piece getPieceCaptured(){
		return captured;
	}


	
}