package RuleSets;
import java.util.*;
import Game.*;
import Pieces.*;
public class Standard8x8 implements IRuleSet {

	public Standard8x8(){

	}

	/**
	* Creates Pieces for the board, and sets up all their positions
	*
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
			if (checkOpponents(move.getPiece(), getPieceAtLocation(pieces, temp_coord))) //only valid diagonal with opponent piece there
				validMoves.add(temp_coord);

			validMoves.add(new Coordinate(cPos.getX()+1, cPos.getY()+0));

			temp_coord = new Coordinate(cPos.getX()+1, cPos.getY()+1);
			if (checkOpponents(move.getPiece(), getPieceAtLocation(pieces, temp_coord))) //only valid diagonal with opponent piece there
				validMoves.add(temp_coord);
		} else {
			temp_coord = new Coordinate(cPos.getX()-1, cPos.getY()-1);
			if (checkOpponents(move.getPiece(), getPieceAtLocation(pieces, temp_coord))) //only valid diagonal with opponent piece there
				validMoves.add(temp_coord);

			validMoves.add(new Coordinate(cPos.getX()+0, cPos.getY()-1));

			temp_coord = new Coordinate(cPos.getX()+1, cPos.getY()-1);
			if (checkOpponents(move.getPiece(), getPieceAtLocation(pieces, temp_coord))) //only valid diagonal with opponent piece there
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
		for (int i = 1; i <= 7; i++)
		{
			Coordinate coord = new Coordinate(cPos.getX() - i, cPos.getY() - i);

			validMoves.add(coord);

			//Checks if we have hit a piece, can't go past it in this direction
			if (getPieceAtLocation(pieces, coord) != null)
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
			if (getPieceAtLocation(pieces, coord) != null)
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
			if (getPieceAtLocation(pieces, coord) != null)
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
			if (getPieceAtLocation(pieces, coord) != null)
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

	private boolean checkMoveRook(ArrayList<Piece> pieces, Move move){
		ArrayList<Coordinate> validMoves = new ArrayList<Coordinate>();
		Coordinate cPos = move.getCurrentPosition();

		//Left
		for (int i = 1; i <= 7; i++)
		{
			Coordinate coord = new Coordinate(cPos.getX() - i, cPos.getY());

			validMoves.add(coord);

			//Checks if we have hit a piece, can't go past it in this direction
			if (getPieceAtLocation(pieces, coord) != null)
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
			if (getPieceAtLocation(pieces, coord) != null)
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
			if (getPieceAtLocation(pieces, coord) != null)
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
			if (getPieceAtLocation(pieces, coord) != null)
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


		//Top Left
		for (int i = 1; i <= 7; i++)
		{
			Coordinate coord = new Coordinate(cPos.getX() - i, cPos.getY() - i);

			validMoves.add(coord);

			//Checks if we have hit a piece, can't go past it in this direction
			if (getPieceAtLocation(pieces, coord) != null)
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
			if (getPieceAtLocation(pieces, coord) != null)
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
			if (getPieceAtLocation(pieces, coord) != null)
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
			if (getPieceAtLocation(pieces, coord) != null)
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
			if (getPieceAtLocation(pieces, coord) != null)
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
			if (getPieceAtLocation(pieces, coord) != null)
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
			if (getPieceAtLocation(pieces, coord) != null)
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
			if (getPieceAtLocation(pieces, coord) != null)
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
	public boolean checkMove(IBoard board, Move move){
		ArrayList<Piece> pieces = board.getPieces();
		Piece piece =  move.getPiece(); //players piece he/she wants to move
		Coordinate nextPostion = move.getNextPosition();
		Piece capture = getPieceAtLocation(pieces, nextPostion);
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
