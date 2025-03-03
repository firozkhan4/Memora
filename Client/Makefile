.SILENT:

SRC_FILES = $(wildcard src/*.java)
MAIN_CLASS = Main
ARGS = $(filter-out $@,$(MAKECMDGOALS))

all: build

build:
	mkdir -p bin
	javac -d bin $(SRC_FILES)

run: build
	java -cp bin $(MAIN_CLASS) $(ARGS)

clean:
	rm -rf bin/*
