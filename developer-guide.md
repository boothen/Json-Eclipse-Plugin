## Setup

* Go to https://www.eclipse.org/downloads and download an eclipse version with RCP support. Download and launch.
* Import existing eclipse projects from the root folder of this repository.
* The ANTLR dependencies will be unresolvable, because the default target platform definition does not include it. 
Select `antlr.target` in the antlr project set it as Target Platform. 
To update the ANTLR version on the remote site, [reficio's p2-maven-plugin](https://github.com/reficio/p2-maven-plugin) can be used.
Alternatively, the site file from the same folder can be used from eclipse to create a new p2 update site.
* Create a new launch configuration with default settings. That mean that the product is `org.eclipse.platform.ide` and 
includes all workspace and enabled target plugins. No custom settings are required.
* Run or debug the application as needed
