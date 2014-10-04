JC = javac
JCF = 
JRE = java
JREF = -Djava.library.path="${PWD}"
javaSOURCE = Game/Coordinate.java Game/Move.java Game/Player.java Game/IBoard.java Game/Controller.java Pieces/Piece.java Pieces/Knight.java Pieces/Bishop.java Pieces/King.java Pieces/Pawn.java Pieces/Queen.java Pieces/Rook.java RuleSets/IRuleSet.java RuleSets/Standard8x8.java
javaCLASS = ${javaSOURCE:.java=.class}
javaMAINCLASS = Game.Controller

default: $(javaCLASS)
	@ echo "Make completed - type 'make run' to run the application."
    
$(javaCLASS): %.class:%.java
	@echo "Building $@..."
	@ $(JC) $(JCF) $<
	
run:
	@ echo "Starting Chess Program..."
	@ $(JRE) $(JREF) $(javaMAINCLASS)
	
clean:
	@ echo "Cleaning Up..."
	@ rm $(javaCLASS)

rebuild: clean default

