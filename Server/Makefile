.SILENT:

SRC_FILES = $(wildcard src/*.java)
MAIN_CLASS = Main

all: build

build:
	mkdir -p bin
	javac -d bin $(SRC_FILES)

run: build
	java -cp bin $(MAIN_CLASS)

clean:
	rm -rf bin/*
