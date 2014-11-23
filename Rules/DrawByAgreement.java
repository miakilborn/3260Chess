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
    
    public DrawByAgreement(Rules rules){
        this.rules = rules;
        this.board = rules.board;
    }

    @Override
    public Result makeMove(Move move) {
//        if (move.isDraw() && rules.getLastMove() != null && rules.getLastMove().isDraw()) {
//            System.err.println(rules.getLastMove().toString());
//            return new Result(true, true, "Confirmed draw");
//        } else if (move.isDraw()){
//            return new Result(true, false, "Draw");
//        }
        return rules.makeMove(move);
    }
}
