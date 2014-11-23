JC = javac
JCF = 
JRE = java
JREF = -Djava.library.path="${PWD}"
javaSOURCE = Test.java Game/Server.java Client/Client.java Game/Board.java Game/GameRoom.java Game/Result.java Game/Coordinate.java Client/View.java Client/TextView.java Game/Move.java Game/Standard8x8Board.java Pieces/Piece.java Pieces/Knight.java Pieces/Bishop.java Pieces/King.java Pieces/Pawn.java Pieces/Queen.java Pieces/Rook.java Rules/BasicRules.java Rules/Promotion.java Rules/Rules.java Rules/RulesDecorator.java
javaCLASS = ${javaSOURCE:.java=.class}
javaMAINCLASS = Client.Client

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
	@ rm -f $(javaCLASS)

rebuild: clean default

