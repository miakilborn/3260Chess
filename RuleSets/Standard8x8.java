public class Standard8x8 implements IRuleSet{

	public Standard8x8(){

	}

	/**
	* Creates Pieces for the board, and sets up all their positions
	*			
	*/
	public void setupBoard(Board board){
		ArrayList<Piece> pieces = new ArrayList<Piece>();
		String colour = "";
		for (int i=0;i<2;i++){
			colour = "White";
			pieces.add(Rook(colour, new Coordinate(1,1)));
			pieces.add(Rook(colour, new Coordinate(8,1)));
			pieces.add(Knight(colour, new Coordinate(2,1)));
			pieces.add(Knight(colour, new Coordinate(7,1)));	
			pieces.add(Bishop(colour, new Coordinate(3,1)));
			pieces.add(Bishop(colour, new Coordinate(6,1)));
			pieces.add(Queen(colour, new Coordinate(4,1)));
			pieces.add(King(colour, new Coordinate(5,1)));
			for (int p=1;p<=8;p++)
				pieces.add(Pawn(colour, new Coordinate(p,2)));

			colour = "Black";
			pieces.add(Rook(colour, new Coordinate(1,8)));
			pieces.add(Rook(colour, new Coordinate(8,8)));
			pieces.add(Knight(colour, new Coordinate(2,8)));
			pieces.add(Knight(colour, new Coordinate(7,8)));	
			pieces.add(Bishop(colour, new Coordinate(3,8)));
			pieces.add(Bishop(colour, new Coordinate(6,8)));
			pieces.add(Queen(colour, new Coordinate(4,8)));
			pieces.add(King(colour, new Coordinate(5,8)));
			for (int p=1;p<=8;p++)
				pieces.add(Pawn(colour, new Coordinate(p,7)));
		}
		for (int i=0;i<pieces.size();i++)
			board.addPiece(pieces.get(i));
	}


	/**
	* Checks if any board provided list of pieces are at the position 'coord'
	* @return	The reference to the Piece object if found otherwise null
	*			
	*/
	private Piece getPieceAtLocation(ArrayList<Piece> pieces, Coordinate coord){
		for (int i=0;i<pieces.size();i++){
			Piece piece = pieces.get(i);
			if (piece.getPosition().equals(coord)){
				return piece;
			}
		}
		return null;
	}


	/**
	* Verify the provided move is valid to this ruleset
	* @return	Sets the valid flag in Move object, and updates the
	*			captured piece in the Move object if tile conflict occurs
	*			
	*/
	public void checkMove(Board board, Move move){
		ArrayList<Piece> pieces = board.getPieces();
		Coordinate nextPostion = move.getNextPosition();
		Piece capture = getPieceAtLocation(pieces, coord);
		//..
		
	}



}