#!/bin/bash
SITE="."

scp -p -r $SITE/*.jar $SITE/site.xml $SITE/features $SITE/plugins $SITE/web $SITE/index.html martin-steiger.de:/var/www/html/jsonedit
