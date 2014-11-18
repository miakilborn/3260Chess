package RuleSets;
import Game.*;
import Pieces.*;
import RuleSets.Rules.IRule;
import java.util.*;
public class Standard8x8 implements IRuleSet {

	private Move lastMove = null;

	public Standard8x8(){

	}

	/**
	* Creates Pieces for the board, and sets up all their positions
	* @author	Tim
	*/
	public void setupBoard(Board board){
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
	private Piece getPieceFromPosition(Board board, Coordinate coord){
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
	public boolean checkMovePawn(Board board, Move move){
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

			temp_coord = new Coordinate(cPos.getX()+0, cPos.getY()+1);
			if (!checkOpponents(piece, getPieceFromPosition(board, temp_coord))) //only valid diagonal with opponent piece there
				validMoves.add(temp_coord);

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

			temp_coord = new Coordinate(cPos.getX()+0, cPos.getY()-1);
			if (!checkOpponents(piece, getPieceFromPosition(board, temp_coord))) //only valid diagonal with opponent piece there
				validMoves.add(temp_coord);

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

	/**
	* @author	Bill
	*/
	public Move getLastMove(){
		return lastMove;
	}

	public boolean checkMoveKnight(Board board, Move move){
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
			if (OnBoard(validMove) == false) //any moves off the board, skip
				continue;
			if (move.getNextPosition().equals(validMove)) //if player's move matches a valid move
				return true;
		}
		return false;
	}

	public boolean checkMoveBishop(Board board, Move move){
		ArrayList<Coordinate> validMoves = new ArrayList<Coordinate>();
		Coordinate cPos = move.getCurrentPosition();

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

	public boolean checkMoveRook(Board board, Move move){
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
		logicalMoves.add(new Coordinate(cPos.getX()+1, cPos.getY()+0));
		logicalMoves.add(new Coordinate(cPos.getX()+1, cPos.getY()-1));
		logicalMoves.add(new Coordinate(cPos.getX()+0, cPos.getY()-1));
		logicalMoves.add(new Coordinate(cPos.getX()-1, cPos.getY()-1));
		logicalMoves.add(new Coordinate(cPos.getX()-1, cPos.getY()+0));

		for (Coordinate logicalMove : logicalMoves){
			if(OnBoard(logicalMove) == false){
				continue;
			}
			validMoves.add(logicalMove);
		}
		return validMoves;
	}

	public boolean checkMoveKing(Board board, Move move){
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

	public boolean checkMoveQueen(Board board, Move move){
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
	private Piece getKing(Board board, String colour){
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
	private Coordinate getKingPosition(Board board, String colour){
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
	public boolean isInCheckMate(Board board, String colour){
		Piece king = getKing(board, colour);
		Coordinate kingCoords = king.getPosition();
		ArrayList<Coordinate> validMoves = getValidMovesKing(kingCoords);


		if (!isInCheck(board, colour))
			return false;

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
	public boolean isInCheck(Board board, String colour){
		ArrayList<Piece> pieces = board.getPieces();
		Coordinate kingCoords = getKingPosition(board, colour);

		for (int i=0;i<pieces.size();i++){
			Piece piece = pieces.get(i);
			if (!piece.getColour().equals(colour)){
				Move move = new Move(colour, piece.getPosition(), kingCoords);
				if (checkMoveByPiece(board, move))
					return true;
			}
		}
		return false;
	}

        /**
         * Performs the move temporarily to verify if the move puts player in check
         * @param board
         * @param colour
         * @author Tim
         * @return
         */

        public boolean isGoingToBeInCheck(Board board, String colour, Move move){
            Piece piece = board.getPieceFromPosition(move.getCurrentPosition());
            Piece capture = board.getPieceFromPosition(move.getNextPosition());
            piece.setPosition(move.getNextPosition());  //Perform the piece move temporarly, check if it makes the player in check who is making the move

            boolean isCheck = false;
            if (capture != null){
                capture.setPosition(new Coordinate(0,0));
            }


            if (isInCheck(board, piece.getColour())){ //this move has made player put him/herself in check, invalid move!
                isCheck = true;
            }

            if (capture != null)
                capture.setPosition(move.getNextPosition());
            piece.setPosition(move.getCurrentPosition());

            return isCheck;
        }

	/**
    * Checks if the opponents king is captured
    * @author Tim
    */
    public boolean hasWon(Board board, String colour){
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
	private boolean checkMoveByPiece(Board board, Move move){
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


	public Result makeMove(Board board, Move move, ArrayList<IRule> additionalRules){
            if (additionalRules != null){
                for (int i=0;i<additionalRules.size();i++){
                    IRule rule = additionalRules.get(i);
                    Result result = rule.checkAndMakeMove(board, this, move);
                    if (result.isValid()){
                        lastMove = move;
                        return result;
                    } else
                        System.err.println("Not applicable: " + result.getMessage());
                }
            }
            //default move system (fallback)
            Piece piece = board.getPieceFromPosition(move.getCurrentPosition());
            Piece cap = board.getPieceFromPosition(move.getNextPosition());
            if(cap != null){
                System.err.println("REMOVING: "+cap.toString());
                board.removePiece(cap);
            }
            lastMove = move;
            piece.setPosition(move.getNextPosition());
            piece.setHasMoved(true);
            return new Result(true);
        }

	/**
	* Verify the provided move is valid to this RuleSet
	* @return	Sets the valid flag in Move object, and updates the
	*			captured piece in the Move object if tile conflict occurs
	* @author	Tim
	*/
	public Result checkMove(Board board, Move move, ArrayList<IRule> additionalRules){
		Piece piece =  board.getPieceFromPosition(move.getCurrentPosition()); //players piece he/she wants to move
		Coordinate nextPostion = move.getNextPosition();
		Piece capture = getPieceFromPosition(board, nextPostion);
                Result ruleResult = null;
		if (capture != null)
			move.setPieceCaptured(nextPostion);

		if ((lastMove != null && lastMove.getColour().equals(move.getColour()))||
                        lastMove == null && move.getColour().equals("Black")){ // If same player is trying to move again, invalid move!
                    return new Result(false, "It is not your turn");
		}

                if (additionalRules != null){ //check additional rules
                    for (int i=0;i<additionalRules.size();i++){
                        IRule rule = additionalRules.get(i);
                        Result result = rule.checkMove(board, this, move);
                        if (result != null && result.isValid()){
                            ruleResult = result;
                        }
                    }
                }

                //If a rule was applied, we ignore this RuleSet and just return here
                if (ruleResult != null){
                    return ruleResult;
                }

                if (capture != null){
                    if (!checkOpponents(capture, piece)){ //if there is collision with the players OWN piece, invalid move!
                        return new Result(false, "You may not attempt to capture your own piece!");
                    }
		}

                if (!checkMoveByPiece(board, move)){
                    return new Result(false, "Generic invalid move");
                }

                // Check for king in check (by temporarly performing the move)
                if (isGoingToBeInCheck(board, piece.getColour(), move)){
                    return new Result(false, "This move puts you in check");
                }

                // If still valid, its a good move
                lastMove = move;
		return new Result(true, "Valid move");
	}

}
