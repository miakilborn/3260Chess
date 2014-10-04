public class Pawn extends Piece {
	private final String colour;
	private Coordinate position;
	public Pawn(){

	}

	public Pawn(String colour, Coordinate position){
		
	}

	public void setPosition(Coordinate position){

	}

	public void setPosition(int x, int y){
		this.position.setX(x);
		this.position.setY(y);
	}

	public Coordinate getPosition(){
		return position;
	}

	public String getColour(){
		return colour;
	}

	/**
	*  set this piece to be captured state, to uncapture
	*  set its position once again
	*/
	public void capture(){
		setPosition(-1, -1)
	}


}

