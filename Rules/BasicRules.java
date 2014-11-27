/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Rules;

import Game.*;
import Pieces.*;
import java.util.ArrayList;

/**
 *
 * @author Tim
 */
public class BasicRules extends Rules {    
    public BasicRules(Board board){
        this.board = board;
    }
    
    @Override
    public Result makeMove(Move move){
        Move lastMove = board.getLastMove();
        if (move.getMoved()){
            board.setLastMove(move);
        }
        Piece piece =  board.getPieceFromPosition(move.getCurrentPosition()); //players piece he/she wants to move
        Coordinate nextPostion = move.getNextPosition();
        Piece capture = board.getPieceFromPosition(nextPostion);
        if (capture != null)
            move.setPieceCaptured(nextPostion);

        if ((lastMove != null && lastMove.getColour().equals(move.getColour()))||
                lastMove == null && move.getColour().equals("Black")){ // If same player is trying to move again, invalid move!
            return new Result(false, "It is not your turn");
        }

        if (capture != null){
            if (!checkOpponents(capture, piece)){ //if there is collision with the players OWN piece, invalid move!
                return new Result(false, "You may not attempt to capture your own piece!");
            }
        }
        
        Result result = checkMoveByPiece(move);
        if (!result.isValid()){
            return result;
        }

        // Check for king in check (by temporarly performing the move)
        if (isGoingToBeInCheck(piece.getColour(), move)){
            return new Result(false, "This move puts/keeps you in check");
        }
        
        if (result.isValid()){
            board.performMove(move);
        }
        
        return result;
    }
    
    /**
    * Checks if specified colour is currently in check
    * @param	board reference, and player colour (White/Black)
    * @author 	Tim
    */
    public boolean isInCheck(Board board, String colour){
        ArrayList<Piece> pieces = board.getPieces();
        Coordinate kingCoords = getKingPosition(colour);

        for (int i=0;i<pieces.size();i++){
            Piece piece = pieces.get(i);
            if (!piece.getColour().equals(colour)){
                Move move = new Move(colour, piece.getPosition(), kingCoords);
                if (checkMoveByPiece(move).isValid())
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
     * @param move
     * @return
     */

    public boolean isGoingToBeInCheck(String colour, Move move){
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
    * Gets the Piece object of the king owned by specified colour
    * @author 	Tim
    */
    private Piece getKing(String colour){
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
    private Coordinate getKingPosition(String colour){
            Piece king = getKing(colour);
            if (king != null)
                return king.getPosition();
            else
                return null;
    }
    
    /**
    * Checks which type of piece is being moved, and calls the appropriate method
    * @author 	Tim
    */
    private Result checkMoveByPiece(Move move){
        Piece piece =  board.getPieceFromPosition(move.getCurrentPosition()); //players piece he/she wants to move
        if (piece instanceof Pawn)
            return checkMovePawn(move);
        else if  (piece instanceof Knight)
            return checkMoveKnight(move);
        else if  (piece instanceof Rook)
            return checkMoveRook(move);
        else if  (piece instanceof Bishop)
            return checkMoveBishop(move);
        else if  (piece instanceof King)
            return checkMoveKing(move);
        else if  (piece instanceof Queen)
            return checkMoveQueen(move);
        else
            return new Result(false);
    }
    
    /**
    * Checks if two pieces provided are of the opposite colour
    * @param	two Piece references
    * @author 	Tim
    */
    private boolean checkOpponents(Piece piece1, Piece piece2){
        if (piece1 != null && piece2 != null) {
            if (piece1.getColour().equals(piece2.getColour())) //if piece colours match, same player owns pieces
                return false;
            else
                return true;
        } else {
            return false;
        }
    }
    
    /**
    * @author	Tim
    */
    @Override
    public Result checkMovePawn(Move move){
        ArrayList<Coordinate> validMoves = new ArrayList<Coordinate>();
        Piece piece = board.getPieceFromPosition(move.getCurrentPosition());
        Coordinate cPos = move.getCurrentPosition();
        String colour = piece.getColour();

        Coordinate temp_coord = null;
        if (colour.equals("White")){
            temp_coord = new Coordinate(cPos.getX()+1, cPos.getY()+1);
            if (checkOpponents(piece, board.getPieceFromPosition(temp_coord))) //only valid diagonal with opponent piece there
                validMoves.add(temp_coord);

            temp_coord = new Coordinate(cPos.getX()+0, cPos.getY()+1);
            if (board.getPieceFromPosition(temp_coord) == null) //only valid forward if no collision
                validMoves.add(temp_coord);

            temp_coord = new Coordinate(cPos.getX()-1, cPos.getY()+1);
            if (checkOpponents(piece, board.getPieceFromPosition(temp_coord))) //only valid diagonal with opponent piece there
                validMoves.add(temp_coord);

            if (!piece.hasMoved()){
                Coordinate spot1 = new Coordinate(cPos.getX(), cPos.getY()+1);
                Coordinate spot2 = new Coordinate(cPos.getX(), cPos.getY()+2);
                if ((board.getPieceFromPosition(spot1) == null) && (board.getPieceFromPosition(spot2) == null))
                        validMoves.add(spot2);
            }
        } else {
            temp_coord = new Coordinate(cPos.getX()-1, cPos.getY()-1);
            if (checkOpponents(piece, board.getPieceFromPosition(temp_coord))) //only valid diagonal with opponent piece there
                validMoves.add(temp_coord);

            temp_coord = new Coordinate(cPos.getX()+0, cPos.getY()-1);
            if (board.getPieceFromPosition(temp_coord) == null) //only valid forward if no collision
                validMoves.add(temp_coord);

            temp_coord = new Coordinate(cPos.getX()+1, cPos.getY()-1);
            if (checkOpponents(piece, board.getPieceFromPosition(temp_coord))) //only valid diagonal with opponent piece there
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
            if (board.isValidCoordinate(validMove) == false) //any moves off the board, skip
                continue;
            if (move.getNextPosition().equals(validMove)) //if player's move matches a valid move
                return new Result(true);
        }
        return new Result(false, "A pawn may not move like that");
    }
    
    @Override
    public Result checkMoveKing(Move move){
        Coordinate cPos = move.getCurrentPosition();

        ArrayList<Coordinate> logicalMoves = new ArrayList<>();
        ArrayList<Coordinate> validMoves = new ArrayList<>();
        logicalMoves.add(new Coordinate(cPos.getX()-1, cPos.getY()+1));
        logicalMoves.add(new Coordinate(cPos.getX()+0, cPos.getY()+1));
        logicalMoves.add(new Coordinate(cPos.getX()+1, cPos.getY()+1));
        logicalMoves.add(new Coordinate(cPos.getX()+1, cPos.getY()+0));
        logicalMoves.add(new Coordinate(cPos.getX()+1, cPos.getY()-1));
        logicalMoves.add(new Coordinate(cPos.getX()+0, cPos.getY()-1));
        logicalMoves.add(new Coordinate(cPos.getX()-1, cPos.getY()-1));
        logicalMoves.add(new Coordinate(cPos.getX()-1, cPos.getY()+0));

        for (Coordinate logicalMove : logicalMoves){
            if(board.isValidCoordinate(logicalMove) == false){
                continue;
            }
            validMoves.add(logicalMove);
        }

        for (int i=0;i<validMoves.size();i++){
            Coordinate validMove = validMoves.get(i);
            if (move.getNextPosition().equals(validMove)) //if player's move matches a valid move
                return new Result(true);
        }
        return new Result(false, "A king may not move like that");
    }
    
    @Override
    public Result checkMoveQueen(Move move){
        Result result = checkMoveRook(move);
        if (!result.isValid()){
            result = checkMoveBishop(move);
        }
        if (result.isValid()){
            return new Result(true);
        } else {
            return new Result(false, "A queen may not move like that");
        }
    }
    
    @Override
    public Result checkMoveBishop(Move move){
        ArrayList<Coordinate> validMoves = new ArrayList<Coordinate>();
        Coordinate cPos = move.getCurrentPosition();

        //Top Left
        for (int i = 1; i <= 7; i++){
            Coordinate coord = new Coordinate(cPos.getX() - i, cPos.getY() - i);

            validMoves.add(coord);

            //Checks if we have hit a piece, can't go past it in this direction
            if (board.getPieceFromPosition(coord) != null){
                break;
            }
        }

        //Top Right
        for (int i = 1; i <= 7; i++){
            Coordinate coord = new Coordinate(cPos.getX() + i, cPos.getY() - i);

            validMoves.add(coord);

            //Checks if we have hit a piece, can't go past it in this direction
            if (board.getPieceFromPosition(coord) != null){
                break;
            }
        }

        //Bottom Left
        for (int i = 1; i <= 7; i++){
            Coordinate coord = new Coordinate(cPos.getX() - i, cPos.getY() + i);

            validMoves.add(coord);

            //Checks if we have hit a piece, can't go past it in this direction
            if (board.getPieceFromPosition(coord) != null){
                break;
            }
        }

        //Bottom Right
        for (int i = 1; i <= 7; i++){
            Coordinate coord = new Coordinate(cPos.getX() + i, cPos.getY() + i);

            validMoves.add(coord);

            //Checks if we have hit a piece, can't go past it in this direction
            if (board.getPieceFromPosition(coord) != null){
                break;
            }
        }
        
        for (int i=0;i<validMoves.size();i++){
            Coordinate validMove = validMoves.get(i);
            if (board.isValidCoordinate(validMove) == false) //any moves off the board, skip
                continue;
            if (move.getNextPosition().equals(validMove)) //if player's move matches a valid move
                return new Result(true);
        }
        return new Result(false, "A bishop may not move like that");
    }
    
    @Override
    public Result checkMoveRook(Move move){
        ArrayList<Coordinate> validMoves = new ArrayList<Coordinate>();
        Coordinate cPos = move.getCurrentPosition();

        //Left
        for (int i = 1; i <= 7; i++){
            Coordinate coord = new Coordinate(cPos.getX() - i, cPos.getY());

            validMoves.add(coord);

            //Checks if we have hit a piece, can't go past it in this direction
            if (board.getPieceFromPosition(coord) != null){
                    break;
            }
        }

        //Right
        for (int i = 1; i <= 7; i++){
            Coordinate coord = new Coordinate(cPos.getX() + i, cPos.getY());

            validMoves.add(coord);

            //Checks if we have hit a piece, can't go past it in this direction
            if (board.getPieceFromPosition(coord) != null){
                break;
            }
        }

        //Top
        for (int i = 1; i <= 7; i++){
            Coordinate coord = new Coordinate(cPos.getX(), cPos.getY() - i);

            validMoves.add(coord);

            //Checks if we have hit a piece, can't go past it in this direction
            if (board.getPieceFromPosition(coord) != null){
                break;
            }
        }

        //Bottom
        for (int i = 1; i <= 7; i++){
            Coordinate coord = new Coordinate(cPos.getX(), cPos.getY() + i);

            validMoves.add(coord);

            //Checks if we have hit a piece, can't go past it in this direction
            if (board.getPieceFromPosition(coord) != null){
                break;
            }
        }

        for (int i=0;i<validMoves.size();i++){
            Coordinate validMove = validMoves.get(i);
            if (board.isValidCoordinate(validMove) == false) //any moves off the board, skip
                continue;
            if (move.getNextPosition().equals(validMove)) //if player's move matches a valid move
                return new Result(true);
        }
        return new Result(false, "A rook may not move like that");
    }
    
    @Override
    public Result checkMoveKnight(Move move){
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
            if (board.isValidCoordinate(validMove) == false) //any moves off the board, skip
                continue;
            if (move.getNextPosition().equals(validMove)) //if player's move matches a valid move
                return new Result(true);
        }
        return new Result(false, "A knight may not move like that");
    }
    
    @Override
    public Board getBoard(){
        return board;
    }
}
