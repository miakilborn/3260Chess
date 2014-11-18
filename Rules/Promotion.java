/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Rules;

import Game.Move;

/**
 *
 * @author Tim
 */
public class Promotion extends RulesDecorator {
    private Rules rules;
    
    public Promotion(Rules rules){
        this.rules = rules;
    }

    @Override
    public boolean checkMove(Move move) {
        boolean valid = rules.checkMove(move);
        if (valid) return true; //if another rule found this move valid
        else {
            //Do promotion checks
            
            return true; //or false;
        }
    }
    
    
}
