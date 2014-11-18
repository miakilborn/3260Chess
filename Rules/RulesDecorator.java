/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Rules;

import Game.*;

/**
 *
 * @author Tim
 */
public abstract class RulesDecorator extends Rules {
    public abstract Result makeMove(Move move);
}
