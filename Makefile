JC = javac
JCF = 
JRE = java
JREF = -Djava.library.path="${PWD}"
javaSOURCE = Controller.java
javaCLASS = ${javaSOURCE:.java=.class}
javaMAINCLASS = Controller

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
	@ rm *.class

rebuild: clean default

