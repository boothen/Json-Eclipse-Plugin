#!/bin/bash

ANTLR_VERSION=4.7.1
FILENAME="antlr-$ANTLR_VERSION-complete.jar"

if ! [ -f $FILENAME ]; then
  URL="http://www.antlr.org/download/$FILENAME"
  echo "Downloading $URL"
  curl -o $FILENAME $URL
else
  echo "File $FILENAME already exists"
fi

echo "Compiling grammar file"
java -jar ${FILENAME} JSONLexer.g4 JSON.g4 -visitor -package com.boothen.jsonedit.antlr -o json-grammar
echo "Compilation done!"

echo "Replacing tabs with spaces"
find json-grammar/*.java -exec sed -i 's/\t/    /g' {} +

echo "Adding a new-line char at the end (if missing)"
find json-grammar/*.java -exec sed -i -e '$a\' {} +

echo "Moving generated Java files"
mv -v json-grammar/*.java ../src/main/java/com/boothen/jsonedit/antlr/
