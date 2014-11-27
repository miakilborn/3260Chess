/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Rules;

import Game.*;
import Pieces.*;

/**
 *
 * @author Tim
 */
public class EnPassent extends RulesDecorator {
    private Rules rules;
    
    public EnPassent(Rules rules){
        this.rules = rules;
        this.board = rules.board;
    }

    public Result checkMove(Move move){
        if(lastMove==null){
            return new Result(false);
        }
        Coordinate current = move.getCurrentPosition();
        Coordinate newP = move.getNextPosition();
        Coordinate lastNextP = lastMove.getNextPosition();
        Coordinate lastCurrent = lastMove.getCurrentPosition();
        if(current==null || newP==null || lastNextP == null || lastCurrent==null){
            return new Result(false);
        }
        Piece myPiece = board.getPieceFromPosition(current);
        Piece theirPiece = board.getPieceFromPosition(lastNextP);
        if(myPiece==null || theirPiece==null){
            return new Result(false);
        }

        if(!(myPiece instanceof Pawn) || !(theirPiece instanceof Pawn)){
            return new Result(false);
        }

        //if last move was not a double jump pawn
        if(Math.abs(lastNextP.getY()-lastCurrent.getY())!=2){
            return new Result(false);
        }

        //if desired move is on an angle
        if(Math.abs(current.getY()-newP.getY())==1 && Math.abs(current.getX()-newP.getX())==1){
            //if desired move puts pawn behind other pawn
            if((lastNextP.getY()==5 && newP.getY()==6 && lastNextP.getX()==newP.getX())
            || (lastNextP.getY()==4 && newP.getY()==3 && lastNextP.getX()==newP.getX())){
                return new Result(true);
            }
        }

        return new Result(false);
    }
    
    
    @Override
    public Result makeMove(Move move) {
        if (checkMove(move).isValid()){
            Piece myPiece = board.getPieceFromPosition(move.getCurrentPosition());
            Piece theirPiece = board.getPieceFromPosition(move.getNextPosition());
            theirPiece.capture();
            myPiece.setPosition(move.getNextPosition());
            move.setMoved(true);
            lastMove = move;
            rules.makeMove(move);
            return new Result(true);
        } else {
            return rules.makeMove(move);
        }
        

    }
    
}
