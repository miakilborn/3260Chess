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
    private Move lastMove; //storage locally to this rule so we don't affect the game
    
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
        if (checkMove(lastMove).isValid() && checkMove(move).isValid() && !lastMove.getColour().equals(move.getColour())) {
            return new Result(true, true, "Confirmed draw");
        } else if (checkMove(move).isValid()){
            lastMove = move;
            return new Result(false, "Draw requested, to cancel request just make your move");
        } else {
            Result result = rules.makeMove(move);
            if (result.isValid()){
                lastMove = move;
            }
            return result;
        }
    }
}
