#!/bin/bash
SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
SITE=$SCRIPT_DIR
TARGET="martin-steiger.de:/var/www/html/jsonedit"

echo "Copying p2 update site content.."
scp -p -r $SITE/*.jar $SITE/site.xml $SITE/features $SITE/plugins $TARGET

echo "Copying website content.."
scp -p -r $SITE/web $SITE/index.html $SITE/scripts $TARGET

# read -r raw input - disables interpretion of backslash escapes and line-continuation in the read data
# read -p the prompt string is output
# read -s to hide typed chars

read -r -p "Press [ENTER] to exit..."


