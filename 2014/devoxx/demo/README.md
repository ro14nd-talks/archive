# Devoxx 2014

This demo contains the chart and chat demo shown at Devoxx (+ an extra undocumented gem ;-).

In order to start the demo:

* Start a Java server you want to monitor
* Attach jolokia : `java -jar jolokia <pid>` 
* Important: Open chart.html or chat.html **via an HTTP server**. The demo doesn't work if opened as file because of cross origin restrictions.


## 2.0 Preview

* Install an empty Tomcat
* Copy `agent/jolokia-2.0.0-SNAPSHOT.war` to `$TC/webapps/jolokia.war`
* Configure Tomcat to listen to port 8181 or adapt `chat.html`
* Goto `mbeans` and run `mvn install`
* Copy `mbeans/target/devoxx-mbeans.war` to `$TC/webapps/`


