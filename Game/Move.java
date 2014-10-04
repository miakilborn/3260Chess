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
		//EXAMPLE: M(Rook(C(0,0),black,false),C(0,1))
		if(s.charAt(0)=='M'){
			s = s.replaceAll("M\\(|\\)$","");
			String p = s.substring(0,s.lastIndexOf("),")+1);
			s = s.replace(p+",","");
			switch(p.substring(0,p.indexOf("("))){
				case "Rook":
					this.piece = new Rook(p);
					break;
				case "Knight":
					this.piece = new Knight(p);
					break;
				case "Bishop":
					this.piece = new Bishop(p);
					break;
				case "King":
					this.piece = new King(p);
					break;
				case "Queen":
					this.piece = new Queen(p);
					break;
				case "Pawn":
					this.piece = new Pawn(p);
					break;
				default:
					this.piece = null;
			}
			nextPosition = new Coordinate(s);
		}
		else{
			piece = null;
			nextPosition = null;
		}

		//Need a basic string constructor... this will rely on a coordinate
		//toString and a coordinate string constructor as well.

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

	public String toString(){
		//Should return a string that you can use in the constructor to rebuild the object.
		return ("M("+piece.toString()+","+nextPosition.toString()+")");
	}
}
