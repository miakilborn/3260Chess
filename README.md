3260Chess
=========

Chess game for CIS*3260 Software Design 4

Assumes there is only 2 players per gameroom, with as many spectators (observers) as one wants.

GROUP MEMBERS
=============
Tim Engel
William Vandenberk
Mia Kilborn
Zachary Licastro


SYSTEM REQUIREMENTS
===================

JDK/JRE >= 1.8


GAME PLAY
=========
To compile the program's source code, navigate to the directory where this README is located in a terminal window. On the command line, run:
'make'

This implementation of chess can be played as hotseat, but has been designed to run smoothly over a network. Given this, Running in hotseat mode requires two instances of the game to be run at the same time. This decision was made as the developers did not want to redesign and rewrite a lot of code to include networking in the future.
Hotseat is defined as one keyboard, so running both game instances locally is considered hotseat.

Instructions to Run:
- Open terminal window #1, compile program
- Open terminal window #2
** both terminal windows need to be open in the same directory as this README

- In a terminal, type make server
- In another terminal, type make run
- In another terminal, type make run

After selecting the same gameroom in both terminals, one terminal should indicate that it is player 1, the other should indicate that it is player 2.

The gameroom selection defines the rules for the game.

Instructions to Play
- Each player (in each terminal window) is assigned a colour, and respective case to represent pieces on the board
- To make a move enter the coordinates of the current position of the piece, followed by the destination coordinates of the piece
- If the extended rules have enabled draw by agreement, type draw instead of a normal move. The other user needs to also type draw in order for the game to end


LIMITATIONS
===========
- Stalemate will not notify of end of game, and doesn't work all the time
- End-game messages are passed, but need to trigger the end game action...

