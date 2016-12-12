JCC=javac
TARGET_DIR=./target
JFLAGS=-g -d $(TARGET_DIR)/ #include debugging info
CLASSPATH=./lib/commons-cli-1.2.jar:./src/
SRCPATH=./src

all: Server.class

Server.class: src/Server.java
	$(JCC) -cp $(CLASSPATH) $(JFLAGS) $(SRCPATH)/Server.java 

clean:
	rm -f $(TARGET_DIR)/*.class
