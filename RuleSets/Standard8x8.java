package RuleSets;
import java.util.*;
import Game.*;
import Pieces.*;
public class Standard8x8 implements IRuleSet {

	private Move lastMove = null;

	public Standard8x8(){

	}

	/**
	* Creates Pieces for the board, and sets up all their positions
	* @author	Tim
	*/
	public void setupBoard(IBoard board){
		ArrayList<Piece> pieces = new ArrayList<Piece>();
		String colour = "";
		
		colour = "White";
		pieces.add(new Rook(colour, new Coordinate(1,1)));
		pieces.add(new Rook(colour, new Coordinate(8,1)));
		pieces.add(new Knight(colour, new Coordinate(2,1)));
		pieces.add(new Knight(colour, new Coordinate(7,1)));
		pieces.add(new Bishop(colour, new Coordinate(3,1)));
		pieces.add(new Bishop(colour, new Coordinate(6,1)));
		pieces.add(new Queen(colour, new Coordinate(4,1)));
		pieces.add(new King(colour, new Coordinate(5,1)));
		for (int p=1;p<=8;p++)
			pieces.add(new Pawn(colour, new Coordinate(p,2)));

		colour = "Black";
		pieces.add(new Rook(colour, new Coordinate(1,8)));
		pieces.add(new Rook(colour, new Coordinate(8,8)));
		pieces.add(new Knight(colour, new Coordinate(2,8)));
		pieces.add(new Knight(colour, new Coordinate(7,8)));
		pieces.add(new Bishop(colour, new Coordinate(3,8)));
		pieces.add(new Bishop(colour, new Coordinate(6,8)));
		pieces.add(new Queen(colour, new Coordinate(4,8)));
		pieces.add(new King(colour, new Coordinate(5,8)));
		for (int p=1;p<=8;p++)
			pieces.add(new Pawn(colour, new Coordinate(p,7)));

		for (int i=0;i<pieces.size();i++)
			board.addPiece(pieces.get(i));
	}


	/**
	* Checks if any board provided list of pieces are at the position 'coord'
	* @return	The reference to the Piece object if found otherwise null
	* @author	Tim
	*/
	private Piece getPieceFromPosition(IBoard board, Coordinate coord){
		return board.getPieceFromPosition(coord);
	}

	/**
	* Checks if a given coordinate is on the board.
	* @return	true if coordinate is on the board, false if it is not.
	* @author	Zack
	*/
	private boolean OnBoard(Coordinate coord){
		if (coord.getX() < 1 || coord.getY() < 1 || coord.getX() > 8|| coord.getY() > 8){
			return false;
		}
		else{
			return true;
		}
	}

	/**
	* @author	Tim
	*/
	private boolean checkMovePawn(IBoard board, Move move){
		ArrayList<Coordinate> validMoves = new ArrayList<Coordinate>();
		Piece piece = board.getPieceFromPosition(move.getCurrentPosition());
		Coordinate cPos = move.getCurrentPosition();
		String colour = piece.getColour();
		ArrayList<Piece> pieces = board.getPieces();

		Coordinate temp_coord = null;
		if (colour.equals("White")){
			temp_coord = new Coordinate(cPos.getX()+1, cPos.getY()+1);
			if (checkOpponents(piece, getPieceFromPosition(board, temp_coord))) //only valid diagonal with opponent piece there
				validMoves.add(temp_coord);

			validMoves.add(new Coordinate(cPos.getX()+0, cPos.getY()+1));

			temp_coord = new Coordinate(cPos.getX()-1, cPos.getY()+1);
			if (checkOpponents(piece, getPieceFromPosition(board, temp_coord))) //only valid diagonal with opponent piece there
				validMoves.add(temp_coord);

			if (!piece.hasMoved()){
				Coordinate spot1 = new Coordinate(cPos.getX(), cPos.getY()+1);
				Coordinate spot2 = new Coordinate(cPos.getX(), cPos.getY()+2);
				if ((board.getPieceFromPosition(spot1) == null) && (board.getPieceFromPosition(spot2) == null))
					validMoves.add(spot2);
			}
		} else {
			temp_coord = new Coordinate(cPos.getX()-1, cPos.getY()-1);
			if (checkOpponents(piece, getPieceFromPosition(board, temp_coord))) //only valid diagonal with opponent piece there
				validMoves.add(temp_coord);

			validMoves.add(new Coordinate(cPos.getX()+0, cPos.getY()-1));

			temp_coord = new Coordinate(cPos.getX()+1, cPos.getY()-1);
			if (checkOpponents(piece, getPieceFromPosition(board, temp_coord))) //only valid diagonal with opponent piece there
				validMoves.add(temp_coord);

			if (!piece.hasMoved()){
				Coordinate spot1 = new Coordinate(cPos.getX(), cPos.getY()-1);
				Coordinate spot2 = new Coordinate(cPos.getX(), cPos.getY()-2);
				if ((board.getPieceFromPosition(spot1) == null) && (board.getPieceFromPosition(spot2) == null))
					validMoves.add(spot2);
			}
		}

		for (int i=0;i<validMoves.size();i++){
			Coordinate validMove = validMoves.get(i);
			if (OnBoard(validMove) == false) //any moves off the board, skip
				continue;
			if (move.getNextPosition().equals(validMove)) //if player's move matches a valid move
				return true;
		}
		return false;
	}

	private boolean checkMoveKnight(IBoard board, Move move){
		ArrayList<Coordinate> validMoves = new ArrayList<Coordinate>();
		Coordinate cPos = move.getCurrentPosition();
		ArrayList<Piece> pieces = board.getPieces();

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
			if (OnBoard(validMove) == false) //any moves off the board, skip
				continue;
			if (move.getNextPosition().equals(validMove)) //if player's move matches a valid move
				return true;
		}
		return false;
	}

	private boolean checkMoveBishop(IBoard board, Move move){
		ArrayList<Coordinate> validMoves = new ArrayList<Coordinate>();
		Coordinate cPos = move.getCurrentPosition();
		ArrayList<Piece> pieces = board.getPieces();

		//Top Left
		for (int i = 1; i <= 7; i++)
		{
			Coordinate coord = new Coordinate(cPos.getX() - i, cPos.getY() - i);

			validMoves.add(coord);

			//Checks if we have hit a piece, can't go past it in this direction
			if (getPieceFromPosition(board, coord) != null)
			{
				break;
			}
		}

		//Top Right
		for (int i = 1; i <= 7; i++)
		{
			Coordinate coord = new Coordinate(cPos.getX() + i, cPos.getY() - i);

			validMoves.add(coord);

			//Checks if we have hit a piece, can't go past it in this direction
			if (getPieceFromPosition(board, coord) != null)
			{
				break;
			}
		}

		//Bottom Left
		for (int i = 1; i <= 7; i++)
		{
			Coordinate coord = new Coordinate(cPos.getX() - i, cPos.getY() + i);

			validMoves.add(coord);

			//Checks if we have hit a piece, can't go past it in this direction
			if (getPieceFromPosition(board, coord) != null)
			{
				break;
			}
		}

		//Bottom Right
		for (int i = 1; i <= 7; i++)
		{
			Coordinate coord = new Coordinate(cPos.getX() + i, cPos.getY() + i);

			validMoves.add(coord);

			//Checks if we have hit a piece, can't go past it in this direction
			if (getPieceFromPosition(board, coord) != null)
			{
				break;
			}
		}



		for (int i=0;i<validMoves.size();i++){
			Coordinate validMove = validMoves.get(i);
			if (OnBoard(validMove) == false) //any moves off the board, skip
				continue;
			if (move.getNextPosition().equals(validMove)) //if player's move matches a valid move
				return true;
		}
		return false;
	}

	private boolean checkMoveRook(IBoard board, Move move){
		ArrayList<Coordinate> validMoves = new ArrayList<Coordinate>();
		Coordinate cPos = move.getCurrentPosition();
		ArrayList<Piece> pieces = board.getPieces();

		//Left
		for (int i = 1; i <= 7; i++)
		{
			Coordinate coord = new Coordinate(cPos.getX() - i, cPos.getY());

			validMoves.add(coord);

			//Checks if we have hit a piece, can't go past it in this direction
			if (getPieceFromPosition(board, coord) != null)
			{
				break;
			}
		}

		//Right
		for (int i = 1; i <= 7; i++)
		{
			Coordinate coord = new Coordinate(cPos.getX() + i, cPos.getY());

			validMoves.add(coord);

			//Checks if we have hit a piece, can't go past it in this direction
			if (getPieceFromPosition(board, coord) != null)
			{
				break;
			}
		}

		//Top
		for (int i = 1; i <= 7; i++)
		{
			Coordinate coord = new Coordinate(cPos.getX(), cPos.getY() - i);

			validMoves.add(coord);

			//Checks if we have hit a piece, can't go past it in this direction
			if (getPieceFromPosition(board, coord) != null)
			{
				break;
			}
		}

		//Bottom
		for (int i = 1; i <= 7; i++)
		{
			Coordinate coord = new Coordinate(cPos.getX(), cPos.getY() + i);

			validMoves.add(coord);

			//Checks if we have hit a piece, can't go past it in this direction
			if (getPieceFromPosition(board, coord) != null)
			{
				break;
			}
		}



		for (int i=0;i<validMoves.size();i++){
			Coordinate validMove = validMoves.get(i);
			if (OnBoard(validMove) == false) //any moves off the board, skip
				continue;
			if (move.getNextPosition().equals(validMove)) //if player's move matches a valid move
				return true;
		}
		return false;
	}

	/**
	* @param current position of the king
	*/
	private ArrayList<Coordinate> getValidMovesKing(Coordinate cPos){
		ArrayList<Coordinate> logicalMoves = new ArrayList<Coordinate>();
		ArrayList<Coordinate> validMoves = new ArrayList<Coordinate>();
		logicalMoves.add(new Coordinate(cPos.getX()-1, cPos.getY()+1));
		logicalMoves.add(new Coordinate(cPos.getX()+0, cPos.getY()+1));
		logicalMoves.add(new Coordinate(cPos.getX()+1, cPos.getY()+1));
		logicalMoves.add(new Coordinate(cPos.getX()-1, cPos.getY()+0));
		logicalMoves.add(new Coordinate(cPos.getX()+1, cPos.getY()+0));
		logicalMoves.add(new Coordinate(cPos.getX()-1, cPos.getY()-1));
		logicalMoves.add(new Coordinate(cPos.getX()+0, cPos.getY()-1));
		logicalMoves.add(new Coordinate(cPos.getX()+1, cPos.getY()-1));

		for (int i=0;i<validMoves.size();i++){
			Coordinate logicalMove = validMoves.get(i);
			if (OnBoard(logicalMove) == false)
				continue;
			validMoves.add(logicalMove);
		}
		return validMoves;
	}

	private boolean checkMoveKing(IBoard board, Move move){
		Coordinate cPos = move.getCurrentPosition();
		ArrayList<Piece> pieces = board.getPieces();

		ArrayList<Coordinate> validMoves = getValidMovesKing(cPos);

		for (int i=0;i<validMoves.size();i++){
			Coordinate validMove = validMoves.get(i);
			if (move.getNextPosition().equals(validMove)) //if player's move matches a valid move
				return true;
		}
		return false;
	}

	private boolean checkMoveQueen(IBoard board, Move move){
		ArrayList<Coordinate> validMoves = new ArrayList<Coordinate>();
		Coordinate cPos = move.getCurrentPosition();
		ArrayList<Piece> pieces = board.getPieces();

		//Top Left
		for (int i = 1; i <= 7; i++)
		{
			Coordinate coord = new Coordinate(cPos.getX() - i, cPos.getY() - i);

			validMoves.add(coord);

			//Checks if we have hit a piece, can't go past it in this direction
			if (getPieceFromPosition(board, coord) != null)
			{
				break;
			}
		}

		//Top
		for (int i = 1; i <= 7; i++)
		{
			Coordinate coord = new Coordinate(cPos.getX(), cPos.getY() - i);

			validMoves.add(coord);

			//Checks if we have hit a piece, can't go past it in this direction
			if (getPieceFromPosition(board, coord) != null)
			{
				break;
			}
		}

		//Top Right
		for (int i = 1; i <= 7; i++)
		{
			Coordinate coord = new Coordinate(cPos.getX() + i, cPos.getY() - i);

			validMoves.add(coord);

			//Checks if we have hit a piece, can't go past it in this direction
			if (getPieceFromPosition(board, coord) != null)
			{
				break;
			}
		}

		//Right
		for (int i = 1; i <= 7; i++)
		{
			Coordinate coord = new Coordinate(cPos.getX() + i, cPos.getY());

			validMoves.add(coord);

			//Checks if we have hit a piece, can't go past it in this direction
			if (getPieceFromPosition(board, coord) != null)
			{
				break;
			}
		}

		//Bottom Right
		for (int i = 1; i <= 7; i++)
		{
			Coordinate coord = new Coordinate(cPos.getX() + i, cPos.getY() + i);

			validMoves.add(coord);

			//Checks if we have hit a piece, can't go past it in this direction
			if (getPieceFromPosition(board, coord) != null)
			{
				break;
			}
		}

		//Bottom
		for (int i = 1; i <= 7; i++)
		{
			Coordinate coord = new Coordinate(cPos.getX(), cPos.getY() + i);

			validMoves.add(coord);

			//Checks if we have hit a piece, can't go past it in this direction
			if (getPieceFromPosition(board, coord) != null)
			{
				break;
			}
		}

		//Bottom Left
		for (int i = 1; i <= 7; i++)
		{
			Coordinate coord = new Coordinate(cPos.getX() - i, cPos.getY() + i);

			validMoves.add(coord);

			//Checks if we have hit a piece, can't go past it in this direction
			if (getPieceFromPosition(board, coord) != null)
			{
				break;
			}
		}

		//Left
		for (int i = 1; i <= 7; i++)
		{
			Coordinate coord = new Coordinate(cPos.getX() - i, cPos.getY());

			validMoves.add(coord);

			//Checks if we have hit a piece, can't go past it in this direction
			if (getPieceFromPosition(board, coord) != null)
			{
				break;
			}
		}


		for (int i=0;i<validMoves.size();i++){
			Coordinate validMove = validMoves.get(i);
			if (OnBoard(validMove) == false) //any moves off the board, skip
				continue;
			if (move.getNextPosition().equals(validMove)) //if player's move matches a valid move
				return true;
		}
		return false;

	}

	/**
	* Gets the Piece object of the king owned by specified colour
	* @param	board reference, and player colour (White/Black)
	* @author 	Tim
	*/
	private Piece getKing(IBoard board, String colour){
		ArrayList<Piece> pieces = board.getPieces();
		for (int i=0;i<pieces.size();i++){ //obtain position of King to that of specified colour
			Piece piece = pieces.get(i);
			if (piece.getColour().equals(colour) && (piece instanceof King)){
				return piece;
			}
		}
		return null;
	}

	/**
	* Gets the position of the king owned by specified colour
	* @param	board reference, and player colour (White/Black)
	* @author 	Tim
	*/
	private Coordinate getKingPosition(IBoard board, String colour){
		Piece king = getKing(board, colour);
		if (king != null)
			return king.getPosition();
		else
			return null;
	}


	/**
	* Checks if specified colour is currently in checkmate
	* @param	board reference, and player colour (White/Black)
	* @author 	Tim
	*/
	public boolean isInCheckMate(IBoard board, String colour){
		Piece king = getKing(board, colour);
		Coordinate kingCoords = king.getPosition();
		ArrayList<Coordinate> validMoves = getValidMovesKing(kingCoords);
		for (int i=0;i<validMoves.size();i++){
			Coordinate validMove = validMoves.get(i);
			Coordinate oldSpot = king.getPosition();
			king.setPosition(validMove);
			if (!isInCheck(board, colour)){
				king.setPosition(oldSpot);
				return false;
			}
			king.setPosition(oldSpot);
		}
		return true;
	}

	/**
	* Checks if specified colour is currently in check
	* @param	board reference, and player colour (White/Black)
	* @author 	Tim
	*/
	public boolean isInCheck(IBoard board, String colour){
		ArrayList<Piece> pieces = board.getPieces();
		Coordinate kingCoords = getKingPosition(board, colour);

		for (int i=0;i<pieces.size();i++){
			Piece piece = pieces.get(i);
			if (!piece.getColour().equals(colour)){
				Move move = new Move(piece.getPosition(), kingCoords);
				if (checkMoveByPiece(board, move))
					return true;
			}
		}
		return false;
	}

	/**
    * Checks if the opponents king is captured
    * @author Tim
    */
    public boolean hasWon(IBoard board, String colour){
        String opponent = "White";
        if (colour.equals("White"))
        	opponent = "Black";
        if (isInCheck(board, opponent) && isInCheckMate(board, opponent))
        	return true;
        else
        	return false;
    }

	/**
	* Checks if two pieces provided are of the opposite colour
	* @param	two Piece references
	* @author 	Tim
	*/
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
	* Checks which type of piece is being moved, and calls the appropriate method
	* @author 	Tim
	*/
	private boolean checkMoveByPiece(IBoard board, Move move){
		Piece piece =  board.getPieceFromPosition(move.getCurrentPosition()); //players piece he/she wants to move
		if (piece instanceof Pawn)
			return checkMovePawn(board, move);
		else if  (piece instanceof Knight)
			return checkMoveKnight(board, move);
		else if  (piece instanceof Rook)
			return checkMoveRook(board, move);
		else if  (piece instanceof Bishop)
			return checkMoveBishop(board, move);
		else if  (piece instanceof King)
			return checkMoveKing(board, move);
		else if  (piece instanceof Queen)
			return checkMoveQueen(board, move);
		else
			return false;
	}


	// public String getTurn(){
	// 	if (lastMove.equals("White"))
	// 		return "Black";
	// 	else
	// 		return "White"
	// }

	/**
	* Verify the provided move is valid to this ruleset
	* @return	Sets the valid flag in Move object, and updates the
	*			captured piece in the Move object if tile conflict occurs
	* @author	Tim
	*/
	public boolean checkMove(IBoard board, Move move){
		boolean validMove = true;
		String message = "";
		ArrayList<Piece> pieces = board.getPieces();
		Piece piece =  board.getPieceFromPosition(move.getCurrentPosition()); //players piece he/she wants to move
		Coordinate nextPostion = move.getNextPosition();
		Piece capture = getPieceFromPosition(board, nextPostion);

		if ((lastMove != null && board.getPieceFromPosition(lastMove.getNextPosition()).getColour().equals(piece.getColour())) ||
				(lastMove == null && piece.getColour().equals("Black"))){ // If same player is trying to move again, invalid move!
			message = "Detected not players turn: " + piece.getColour();
			validMove = false;
		} 
		else if (capture != null){
			if (!checkOpponents(capture, piece)){ //if there is collision with the players OWN piece, invalid move!
				message = "Detected collision with own piece: " + piece.getColour();
				validMove = false;
			}
		} else if (checkMoveByPiece(board, move)) {
			if (validMove){
				Coordinate oldSpot = piece.getPosition();
				piece.setPosition(move.getNextPosition());  //Perform the piece move temporarly, check if it makes the player in check who is making the move
				if (isInCheck(board, piece.getColour())){ //this move has made player put him/herself in check, invalid move!
					message = "Detected king in check for colour: " + piece.getColour();
					validMove = false;
				}
				piece.setPosition(oldSpot);
			}

			if (validMove){
				lastMove = move;
			}
		} else {
			validMove = false;
			message = "generic invalid move";
		}

		System.out.println("Move result, move: " + move + ", valid: " + validMove + (validMove ? "" :  ", msg: " + message));
		return validMove;
	}

}
