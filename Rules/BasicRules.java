/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Rules;

import Game.*;
import Pieces.Bishop;
import Pieces.King;
import Pieces.Knight;
import Pieces.Pawn;
import Pieces.Piece;
import Pieces.Queen;
import Pieces.Rook;
import java.util.ArrayList;

/**
 *
 * @author Tim
 */
public class BasicRules extends Rules {
    private Move lastMove = null;
    
    public BasicRules(Board board){
        this.board = board;
    }
    
    @Override
    public Result makeMove(Move move){
        if (move.getMoved()){
            lastMove = move;
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
//        if (isGoingToBeInCheck(board, piece.getColour(), move)){
//            return new Result(false, "This move puts you in check");
//        }
        if (result.isValid()){
            board.performMove(move);
        }

        // If still valid, its a good move
        lastMove = move;
        return result;
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
}
