#!/bin/bash

if [[ -z "$1" ]] ; then
  echo 'Please specify the path to target folder'
  exit 0
fi

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
SITE=$SCRIPT_DIR
TARGET=$1 

echo "Copying p2 update site content.."
cp -v -r $SITE/*.jar $SITE/site.xml $SITE/features $SITE/plugins $TARGET

echo "Copying website content.."
cp -v -r $SITE/web $SITE/index.html $SITE/scripts $TARGET


