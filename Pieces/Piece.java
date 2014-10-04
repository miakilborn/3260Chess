public abstract class Piece {

	public void setPosition(Coordinate position);

	public Coordinate getPosition();

	public String getColour();

	/**
	*  set this piece to be captured state, to uncapture
	*  set its position once again
	*/
	public void capture();


}

