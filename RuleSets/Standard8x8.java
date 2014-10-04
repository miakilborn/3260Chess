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
		for (int i=0;i<2;i++){
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
		}
		for (int i=0;i<pieces.size();i++)
			board.addPiece(pieces.get(i));
	}


	/**
	* Checks if any board provided list of pieces are at the position 'coord'
	* @return	The reference to the Piece object if found otherwise null
	* @author	Tim
	*/
	private Piece getPieceAtPosition(IBoard board, Coordinate coord){
		return board.getPieceAtPosition(coord);
	}

	/**
	* @author	Tim
	*/
	private boolean checkMovePawn(IBoard board, Move move){
		ArrayList<Coordinate> validMoves = new ArrayList<Coordinate>();
		Coordinate cPos = move.getCurrentPosition();
		String colour = move.getPiece().getColour();
		ArrayList<Piece> pieces = board.getPieces();

		Coordinate temp_coord = null;
		if (colour.equals("White")){
			temp_coord = new Coordinate(cPos.getX()+1, cPos.getY()-1);
			if (checkOpponents(move.getPiece(), getPieceAtPosition(board, temp_coord))) //only valid diagonal with opponent piece there
				validMoves.add(temp_coord);

			validMoves.add(new Coordinate(cPos.getX()+1, cPos.getY()+0));

			temp_coord = new Coordinate(cPos.getX()+1, cPos.getY()+1);
			if (checkOpponents(move.getPiece(), getPieceAtPosition(board, temp_coord))) //only valid diagonal with opponent piece there
				validMoves.add(temp_coord);
		} else {
			temp_coord = new Coordinate(cPos.getX()-1, cPos.getY()-1);
			if (checkOpponents(move.getPiece(), getPieceAtPosition(board, temp_coord))) //only valid diagonal with opponent piece there
				validMoves.add(temp_coord);

			validMoves.add(new Coordinate(cPos.getX()+0, cPos.getY()-1));

			temp_coord = new Coordinate(cPos.getX()+1, cPos.getY()-1);
			if (checkOpponents(move.getPiece(), getPieceAtPosition(board, temp_coord))) //only valid diagonal with opponent piece there
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
			if (validMove.getX() < 1 || validMove.getY() < 1 || validMove.getX() > 8|| validMove.getY() > 8) //any moves off the board, skip
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
			if (getPieceAtPosition(board, coord) != null)
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
			if (getPieceAtPosition(board, coord) != null)
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
			if (getPieceAtPosition(board, coord) != null)
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
			if (getPieceAtPosition(board, coord) != null)
			{
				break;
			}
		}



		for (int i=0;i<validMoves.size();i++){
			Coordinate validMove = validMoves.get(i);
			if (validMove.getX() < 1 || validMove.getY() < 1 || validMove.getX() > 8|| validMove.getY() > 8) //any moves off the board, skip
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
			if (getPieceAtPosition(board, coord) != null)
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
			if (getPieceAtPosition(board, coord) != null)
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
			if (getPieceAtPosition(board, coord) != null)
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
			if (getPieceAtPosition(board, coord) != null)
			{
				break;
			}
		}



		for (int i=0;i<validMoves.size();i++){
			Coordinate validMove = validMoves.get(i);
			if (validMove.getX() < 1 || validMove.getY() < 1 || validMove.getX() > 8|| validMove.getY() > 8) //any moves off the board, skip
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
			if (logicalMove.getX() < 1 || logicalMove.getY() < 1 || logicalMove.getX() > 8|| logicalMove.getY() > 8) 
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
			if (getPieceAtPosition(board, coord) != null)
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
			if (getPieceAtPosition(board, coord) != null)
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
			if (getPieceAtPosition(board, coord) != null)
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
			if (getPieceAtPosition(board, coord) != null)
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
			if (getPieceAtPosition(board, coord) != null)
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
			if (getPieceAtPosition(board, coord) != null)
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
			if (getPieceAtPosition(board, coord) != null)
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
			if (getPieceAtPosition(board, coord) != null)
			{
				break;
			}
		}


		for (int i=0;i<validMoves.size();i++){
			Coordinate validMove = validMoves.get(i);
			if (validMove.getX() < 1 || validMove.getY() < 1 || validMove.getX() > 8|| validMove.getY() > 8) //any moves off the board, skip
				continue;
			if (move.getNextPosition().equals(validMove)) //if player's move matches a valid move
				return true;
		}
		return false;

	}

	/**
	* Gets the Piece object of the king owned by specified colour
	* @param	board reference, and player colour (White/Black)
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
	*/
	public boolean isInCheckMate(IBoard board, String colour){
		Piece king = getKing(board, colour);
		Coordinate kingCoords = king.getPosition();
		ArrayList<Coordinate> validMoves = getValidMovesKing(kingCoords);
		for (int i=0;i<validMoves.size();i++){
			Coordinate validMove = validMoves.get(i);
			Move move = new Move(king, validMove);
			board.makeMove(move);
			Move moveBack = new Move(king, move.getCurrentPosition());
			if (!isInCheck(board, colour)){
				board.makeMove(moveBack);
				return false;
			}
			board.makeMove(moveBack);
		}
		return true;
	}

	/**
	* Checks if specified colour is currently in check
	* @param	board reference, and player colour (White/Black)
	*/
	public boolean isInCheck(IBoard board, String colour){
		ArrayList<Piece> pieces = board.getPieces();
		Coordinate kingCoords = getKingPosition(board, colour);

		for (int i=0;i<pieces.size();i++){
			Piece piece = pieces.get(i);
			if (!piece.getColour().equals(colour)){
				Move move = new Move(piece, kingCoords);
				if (checkMoveByPiece(board, move))
					return true;
			}
		}
		return false;
	}

	/**
	* Checks if two pieces provided are of the opposite colour
	* @param	two Piece references
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

	private boolean checkMoveByPiece(IBoard board, Move move){
		Piece piece =  move.getPiece(); //players piece he/she wants to move
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


	/**
	* Verify the provided move is valid to this ruleset
	* @return	Sets the valid flag in Move object, and updates the
	*			captured piece in the Move object if tile conflict occurs
	* @author	Tim
	*/
	public boolean checkMove(IBoard board, Move move){
		boolean validMove = true;
		ArrayList<Piece> pieces = board.getPieces();
		Piece piece =  move.getPiece(); //players piece he/she wants to move
		Coordinate nextPostion = move.getNextPosition();
		Piece capture = getPieceAtPosition(board, nextPostion);

		if (lastMove != null && lastMove.getPiece().getColour().equals(move.getPiece().getColour())) // If same player is trying to move again, invalid move!
			return false;

		if (capture != null){
			if (!checkOpponents(capture, piece)) //if there is collision with the players OWN piece, invalid move!
				return false;
		}

		validMove = checkMoveByPiece(board, move);


		if (validMove){ //Perform the piece move temporarly, check if it makes the player in check who is making the move
			Move oldSpot = new Move(piece, piece.getPosition());
			board.makeMove(move);
			if (isInCheck(board, piece.getColour())) //this move has made player put him/herself in check, invalid move!
				validMove = false;
			board.makeMove(oldSpot);
		}

		if (validMove){
			lastMove = move;
		}
		return validMove;

	}

}
