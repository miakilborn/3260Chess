3260Chess
=========

Chess game for CIS*3260 Software Design 4
Assumptions:
- 2 players per chess game
- Many observers per chess game

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

This implementation of chess runs on a client-server model architecture.
When the server is started, the number of game rooms is dynamically decided by the starter of the server. Additionally, the rules are selected for each game room, by the person starting the server. 

RUNNING
=======
Compile the program(s) by opening a terminal in the same directory as this readme file and running the command 'make'
Server:
	- To start the server, type 'make server'
Client(s):
	- To start as a client type 'make run'

Once the server is started and configured, clients may connect to the server and join the game room of their choice.
Clients must choose the gameroom they wish to join, by number. Each gameroom has the rules described to help the client choose their game room.
If the selected gameroom already has two player, the thrid, fourth, etc. client to join the room will be added as an observer.

Instructions to Play
- Each player (in each terminal window) is assigned a colour, and respective case to represent pieces on the board
- To make a move enter the coordinates of the current position of the piece, followed by the destination coordinates of the piece
- If the extended rules have enabled draw by agreement, type draw instead of a normal move. The other user needs to also type draw in order for the game to end


LIMITATIONS
===========
- Stalemate will not notify of end of game, and doesn't work all the time
- End-game messages are passed, but need to trigger the end game action...

