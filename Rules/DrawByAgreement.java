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

    @Override
    public Result makeMove(Move move) {
        Result result = null;
        if (move.isDraw() && lastMove != null && lastMove.isDraw() && !lastMove.getColour().equals(move.getColour())) {
            result = new Result(true, true, "Confirmed draw");
        } else if (move.isDraw()){
            lastMove = move;
            result = new Result(true, false, "Draw");
        } else {
            return rules.makeMove(move);
        }
        rules.makeMove(move);
        return result;
    }
}
