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
* @author Tim and MIA. BITCH I DID THIIIIIIINGS.
*/
public class GameRoom extends Observable{
    Board board;
    Rules rules;
    int numPlayers;
    boolean gameOver;
    public String description = "";

    public GameRoom(char[] ruleDef){
        board = new Standard8x8Board();
        rules = new BasicRules(board);

        for(char letter : ruleDef){
            switch(letter){
                case 'c':
                    rules = new Castling(rules);
                    this.description += "Castling ";
                    break;
                case 'd':
                    rules = new DrawByAgreement(rules);
                    this.description += "Draw-by-Agreement ";
                    break;
                case 'p':
                    rules = new Promotion(rules);
                    this.description += "Promotion ";
                    break;
                case 'f':
                    rules = new FiftyMoveRule(rules);
                    this.description += "Fifty-Move ";
                    break;
            }
        }

        rules.setupBoard(board);
        numPlayers = 0;
    }

    public void setChanged(){
        super.setChanged();
    }
    
    public void setGameOver(){
        gameOver = true;
    }
    
    public boolean isGameOver(){
        return gameOver;
    }

    public String toString(){
        if (this.description != "") {
            return "Enabled rules- " + this.description.trim();
        } else {
            return "Basic ruleset";
        }
    }
}
