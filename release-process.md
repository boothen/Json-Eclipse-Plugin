# Update version number

To upgrade the version, quite a few things need to be adjusted. 

### Update all Maven files:

The following snippet updates the version entry in all pom.xml files:

```bash
mvn versions:set -DnewVersion=1.0.0.final -DgenerateBackupPoms=false
```

or

```bash
mvn versions:set -DnewVersion=1.0.0.final
mvn versions:commit
```

### Eclipse manifest files

**Note:** I suspect that there is an easier way, probably through Tycho: 
auto-generate eclipse files or use mvn install directly to build the target site?

* Manually open and edit the version entry in the different plugins.
* Open and adjust the version in the `feature.xml` file
* Open the `site.xml` and edit the XML content directly: add a new entry for the new version. 
Go back to "Site Map" and build only the new feature - the older ones are unresolvable.

# Update Site

* Copy the relevant files to the root folder in the `gh-pages` branch.
Easiest to do with the branch checked out in a separate folder.
* Add all files to the git index and compare with that last commit: `git whatchanged -1`
It should look identical.

* Create a commit and push it.
* Verify with a new (minimal) installation of eclipse:
http://download.eclipse.org/eclipse/downloads/ 
(Select a version and download *Platform Runtime Binary*) - it does not contain the Marketplace plugin though (MPC).
* Download and installation process should work fine

# Marketplace

Go to https://marketplace.eclipse.org/content/json-editor-plugin and add a new version feature.
It might not show up instantly in Marketplace, but it should work.

