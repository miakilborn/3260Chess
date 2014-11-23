
import Game.*;
import java.util.Scanner;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tim
 */
public class Test {
    public static void main(String []args) {
        System.out.println("M(C(-1,-1),C(-2,-2),false,false,)");
        Move move = new Move("", new Coordinate(-1,-1), new Coordinate(-2,-2));
        System.out.println(new Move(move.toString()));
        while (true);
    }
}
