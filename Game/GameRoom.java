/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Game;

import Rules.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.*;
import java.util.*;
/**
*
* @author Tim
*/
public class GameRoom extends Observable{
    Board board;
    Rules rules;
    int numPlayers;

    public GameRoom(){
        board = new Standard8x8Board();
        rules = new Promotion(new BasicRules(board));
        rules.setupBoard(board);
        numPlayers = 0;
    }

    public void setChanged(){
        super.setChanged();
    }

    public String toString(){
        return "Default Game Room";
    }
}
