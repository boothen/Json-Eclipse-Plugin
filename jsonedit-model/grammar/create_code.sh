#!/bin/bash

ANTLR_VERSION=4.5.2
FILENAME="antlr-$ANTLR_VERSION-complete.jar"

if ! [ -f $FILENAME ]; then
  URL="http://www.antlr.org/download/$FILENAME"
  echo "Downloading $URL"
  curl -o $FILENAME $URL
else
  echo "File $FILENAME already exists"
fi

echo "Compiling grammar file"
java -jar antlr-4.5.2-complete.jar JSON.g4 -visitor -package com.boothen.jsonedit.antlr -o json-grammar
echo "Compilation done!"

echo "Moving generated Java files"
mv -v json-grammar/*.java ../src/main/java/com/boothen/jsonedit/antlr/
