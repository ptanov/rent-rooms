You should have installed lastest version of:
  * Eclipse with Google plugins
  * GWT SDK
  * Appengine SDK
  * maven
  * mercurial (hg)

# Import/run in Eclipse #
  * `hg clone https://code.google.com/p/rent-rooms/`
  * `mvn eclipse:eclipse`
  * `mvn gae:unpack`
  * import project in Eclipse
  * In Eclipse:
    * properties of project, `Google > Web toolkit > Use default SDK`
    * properties of project, `Google > Web Application` > Check `Launch and deploy from this directory` (_if not checked then Datanucleus enhanced classes are not copied to WEB-INF/classes, why?!_) (`WAR directory` should be `src/main/webapp`)
    * run project as Web application
    * select `src/main/webapp` in `WAR Directory Selection`

# Run with maven #
  * `hg clone https://code.google.com/p/rent-rooms/`
  * `mvn clean gae:run`

# Deploy to Google AppEngine with Maven #
  * `set MAVEN_OPTS=-Xmx1024m -XX:MaxPermSize=128m`
  * `mvn clean gae:deploy`

# Generate translations #
  * `mvn clean gwt:compile -Dgwt.extraParam=true`
  * look at rent-rooms/target/extra/**.properties**

# Mylyn #
  * From Eclipse Indigo marketplace install Mylyn (3.6)
  * Add Google code Mylyn connector, update site: http://knittig.de/googlecode-mylyn-connector/update/ ( http://code.google.com/p/nemadiy/wiki/Eclipse_Environment , step 8-13)
  * Show View (Window>View) "Task repositories", add new Google Code repository with URL "http://code.google.com/p/rent-rooms"
  * In preferences: Mylyn > Team, set template:
```
(#${task.key}): ${task.description}
${task.url}
```