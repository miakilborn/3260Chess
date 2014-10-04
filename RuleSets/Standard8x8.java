public class Standard8x8 implements IRuleSet {

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

	private boolean checkMovePawn(ArrayList<Piece> pieces, Move move){
		ArrayList<Coordinate> validMoves = new ArrayList<Coordinate>();
		Coordinate cPos = move.getCurrentPosition();
		String colour = move.getPiece().getColour();
		Coordinate temp_coord = null;
		if (colour.equals("White")){
			temp_coord = new Coordinate(cPos.getX()+1, cPos.getY()-1);
			if (checkOpponents(move.getPiece(), getPieceAtLocation(temp_coord))) //only valid diagonal with opponent piece there
				validMoves.add(temp_coord);

			validMoves.add(new Coordinate(cPos.getX()+1, cPos.getY()+0));

			temp_coord = new Coordinate(cPos.getX()+1, cPos.getY()+1);
			if (checkOpponents(move.getPiece(), getPieceAtLocation(temp_coord))) //only valid diagonal with opponent piece there
				validMoves.add(temp_coord);
		} else {
			temp_coord = new Coordinate(cPos.getX()-1, cPos.getY()-1));
			if (checkOpponents(move.getPiece(), getPieceAtLocation(temp_coord))) //only valid diagonal with opponent piece there
				validMoves.add(temp_coord);

			validMoves.add(new Coordinate(cPos.getX()+0, cPos.getY()-1));

			temp_coord = new Coordinate(cPos.getX()+1, cPos.getY()-1));
			if (checkOpponents(move.getPiece(), getPieceAtLocation(temp_coord))) //only valid diagonal with opponent piece there
				validMoves.add(temp_coord);
		}

		for (int i=0;i<validMoves.size();i++){
			Coordinate validMove = validMoves.get(i);
			if (validMove.getX() < 1 || validMove.getY() < 1) //any moves off the board, skip
				continue;
			if (move.getNextPosition().equals(validMove)) //if player's move matches a valid move
				return true;
		}
		return false;
	}

	private boolean checkMoveKnight(ArrayList<Piece> pieces, Move move){
		ArrayList<Coordinate> validMoves = new ArrayList<Coordinate>();
		Coordinate cPos = move.getCurrentPosition();
		validMoves.add(new Coordinate(cPos.getX()-2, cPos.getY()+1));
		validMoves.add(new Coordinate(cPos.getX()-1, cPos.getY()+2));
		validMoves.add(new Coordinate(cPos.getX()+1, cPos.getY()+2));
		validMoves.add(new Coordinate(cPos.getX()+2, cPos.getY()+1));

		validMoves.add(new Coordinate(cPos.getX()-2, cPos.getY()-1));
		validMoves.add(new Coordinate(cPos.getX()-1, cPos.getY()-2));
		validMoves.add(new Coordinate(cPos.getX()+2, cPos.getY()-1));
		validMoves.add(new Coordinate(cPos.getX()+1, cPos.getY()-2));

		for (int i=0;i<validMoves.size();i++){
			Coordinate validMove = validMoves.get(i);
			if (validMove.getX() < 1 || validMove.getY() < 1 || validMove.getX() > 8|| validMove.getY() > 8) //any moves off the board, skip
				continue;
			if (move.getNextPosition().equals(validMove)) //if player's move matches a valid move
				return true;
		}
		return false;
	}

	private boolean checkMoveBishop(ArrayList<Piece> pieces, Move move){
		ArrayList<Coordinate> validMoves = new ArrayList<Coordinate>();
		Coordinate cPos = move.getCurrentPosition();
		
		//Top Left
		validMoves.add(new Coordinate(cPos.getX()-1, cPos.getY()-1));
		validMoves.add(new Coordinate(cPos.getX()-2, cPos.getY()-2));
		validMoves.add(new Coordinate(cPos.getX()-3, cPos.getY()-3));
		validMoves.add(new Coordinate(cPos.getX()-4, cPos.getY()-4));
		validMoves.add(new Coordinate(cPos.getX()-5, cPos.getY()-5));
		validMoves.add(new Coordinate(cPos.getX()-6, cPos.getY()-6));
		validMoves.add(new Coordinate(cPos.getX()-7, cPos.getY()-7));
		
		//Bottom Right
		validMoves.add(new Coordinate(cPos.getX()+1, cPos.getY()+1));
		validMoves.add(new Coordinate(cPos.getX()+2, cPos.getY()+2));
		validMoves.add(new Coordinate(cPos.getX()+3, cPos.getY()+3));
		validMoves.add(new Coordinate(cPos.getX()+4, cPos.getY()+4));
		validMoves.add(new Coordinate(cPos.getX()+5, cPos.getY()+5));
		validMoves.add(new Coordinate(cPos.getX()+6, cPos.getY()+6));
		validMoves.add(new Coordinate(cPos.getX()+7, cPos.getY()+7));
		
		//Bottom Left
		validMoves.add(new Coordinate(cPos.getX()-1, cPos.getY()+1));
		validMoves.add(new Coordinate(cPos.getX()-2, cPos.getY()+2));
		validMoves.add(new Coordinate(cPos.getX()-3, cPos.getY()+3));
		validMoves.add(new Coordinate(cPos.getX()-4, cPos.getY()+4));
		validMoves.add(new Coordinate(cPos.getX()-5, cPos.getY()+5));
		validMoves.add(new Coordinate(cPos.getX()-6, cPos.getY()+6));
		validMoves.add(new Coordinate(cPos.getX()-7, cPos.getY()+7));
		
		//Top Right
		validMoves.add(new Coordinate(cPos.getX()+1, cPos.getY()-1));
		validMoves.add(new Coordinate(cPos.getX()+2, cPos.getY()-2));
		validMoves.add(new Coordinate(cPos.getX()+3, cPos.getY()-3));
		validMoves.add(new Coordinate(cPos.getX()+4, cPos.getY()-4));
		validMoves.add(new Coordinate(cPos.getX()+5, cPos.getY()-5));
		validMoves.add(new Coordinate(cPos.getX()+6, cPos.getY()-6));
		validMoves.add(new Coordinate(cPos.getX()+7, cPos.getY()-7));
		
		for (int i=0;i<validMoves.size();i++){
			Coordinate validMove = validMoves.get(i);
			if (validMove.getX() < 1 || validMove.getY() < 1 || validMove.getX() > 8|| validMove.getY() > 8) //any moves off the board, skip
				continue;
			if (move.getNextPosition().equals(validMove)) //if player's move matches a valid move
				return true;
		}
		return false;
<<<<<<< HEAD

=======
>>>>>>> b294ceb74b39d2901720c150fa2d273191753c03
	}

	private boolean checkMoveKing(ArrayList<Piece> pieces, Move move){
		ArrayList<Coordinate> validMoves = new ArrayList<Coordinate>();
		Coordinate cPos = move.getCurrentPosition();
		validMoves.add(new Coordinate(cPos.getX()-1, cPos.getY()+1));
		validMoves.add(new Coordinate(cPos.getX()+0, cPos.getY()+1));
		validMoves.add(new Coordinate(cPos.getX()+1, cPos.getY()+1));
		validMoves.add(new Coordinate(cPos.getX()-1, cPos.getY()+0));
		validMoves.add(new Coordinate(cPos.getX()+1, cPos.getY()+0));
		validMoves.add(new Coordinate(cPos.getX()-1, cPos.getY()-1));
		validMoves.add(new Coordinate(cPos.getX()+0, cPos.getY()-1));
		validMoves.add(new Coordinate(cPos.getX()+1, cPos.getY()-1));

		for (int i=0;i<validMoves.size();i++){
			Coordinate validMove = validMoves.get(i);
			if (validMove.getX() < 1 || validMove.getY() < 1 || validMove.getX() > 8|| validMove.getY() > 8) //any moves off the board, skip
				continue;
			if (move.getNextPosition().equals(validMove)) //if player's move matches a valid move
				return true;
		}
		return false;

	}

	private boolean checkMoveQueen(ArrayList<Piece> pieces, Move move){
		ArrayList<Coordinate> validMoves = new ArrayList<Coordinate>();
		Coordinate cPos = move.getCurrentPosition();

	}

	private boolean checkOpponents(Piece piece1, Piece piece2){
		if (piece1 != null && piece2 != null)
			if (piece1.getColour().equals(piece2.getColour())) //if piece colours match, same player owns pieces
				return false;
			else
				return true;
		else
			return false;
	}


	/**
	* Verify the provided move is valid to this ruleset
	* @return	Sets the valid flag in Move object, and updates the
	*			captured piece in the Move object if tile conflict occurs
	*			
	*/
	public boolean checkMove(Board board, Move move){
		ArrayList<Piece> pieces = board.getPieces();
		Piece piece =  move.getPiece(); //players piece he/she wants to move
		Coordinate nextPostion = move.getNextPosition();
		Piece capture = getPieceAtLocation(pieces, coord);
		if (capture != null){
			if (!checkOpponents(capture, piece)) //if there is collision with the players OWN piece, invalid move!
				return false;
		}

		if (piece instanceof Pawn)
			return checkMovePawn(pieces, move);
		else if  (piece instanceof Knight)
			return checkMoveKnight(pieces, move);
		else if  (piece instanceof Rook)
			return checkMoveRook(pieces, move);
		else if  (piece instanceof Bishop)
			return checkMoveBishop(pieces, move);
		else if  (piece instanceof King)
			return checkMoveKing(pieces, move);
		else if  (piece instanceof Queen)
			return checkMoveQueen(pieces, move);
		return true;

	}



}
