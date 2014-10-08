package Game;
import Pieces.*;
import Game.*;
public class Move {
	private boolean valid;
	private final Coordinate currentPosition;
	private final Coordinate nextPosition;

	private Piece captured;

	public Move(Coordinate currentPosition, Coordinate nextPosition){
		this.currentPosition = currentPosition;
		this.nextPosition = nextPosition;
	}

	public Move(String s){
		//EXAMPLE: M(C(0,0),C(0,1))
		System.out.println("Str: " + s);
		if(s.charAt(0)=='M'){
			s = s.replaceAll("M\\(|\\)$","");
			System.out.println("Str: " + s);
			String p = s.substring(0,s.lastIndexOf("),")+1);
			s = s.replace(p+",","");

			currentPosition = new Coordinate(p);
			nextPosition = new Coordinate(s);
		}
		else{
			currentPosition = null;
			nextPosition = null;
		}

	}

	public boolean isValid(){
		return valid;
	}

	public Coordinate getCurrentPosition(){
		return currentPosition;
	}

	public Coordinate getNextPosition(){
		return nextPosition;
	}

	public Piece getPieceCaptured(){
		return captured;
	}

	public void setPieceCaptured(Piece piece){
		captured = piece;
	}

	public void setValid(boolean valid){
		this.valid = valid;
	}

	public String toString(){
		//Should return a string that you can use in the constructor to rebuild the object.
		return ("M("+currentPosition.toString()+","+nextPosition.toString()+")");
	}
}
