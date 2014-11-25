/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Rules;

import Game.Move;
import Game.Result;

/**
 *
 * @author Tim
 */
public class DrawByAgreement extends RulesDecorator {
    private Rules rules;
    private Move lastMove;
    
    public DrawByAgreement(Rules rules){
        this.rules = rules;
        this.board = rules.board;
    }
    
    public Result checkMove(Move move){
        if (move != null && move.getData() != null && move.getData().equalsIgnoreCase("draw")){
            return new Result(true, "Draw move");
        } else {
            return new Result(false, "Not a draw move");
        }
    }

    @Override
    public Result makeMove(Move move) {
        Result result = null;
        if (checkMove(lastMove).isValid() && checkMove(move).isValid()) {
            result = new Result(true, true, "Confirmed draw");
        } else if (checkMove(move).isValid()){
            lastMove = move;
            result = new Result(true, false, "PROMPT|Other player requested draw, type 'draw' to confirm");
        } else {
            return rules.makeMove(move);
        }
        rules.makeMove(move);
        return result;
    }
}
