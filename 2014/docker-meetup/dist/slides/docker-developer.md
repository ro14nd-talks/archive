
# Docker for Developers

# Docker is ...

* ... a **lightweight** OS-level virtualization framework
* ... **portable** 
* ... **super fast** and **scalable**
* ... **automatable** via Dockerfiles
* ... provides "**social imaging**" via hub.docker.io


# Crashing into Docker

* DEMO


# Docker Architecture

* Client-Server
	- Docker daemon listens on Unix-Socket or via REST API
	- CLI commands for the client
* Based on Unix isolation techniques
	- cgroups, chroot, ...
	- managed by **Linux Container** (\< 0.9) and then **libcontainer** (\>= 0.9)
* Written in Go


# Docker Entities

* **Image** is a "blueprint" for building *Container*
* **Container** sind instances of *Images* (N:1) an can be running or stopped.
* **Repository** is a named collection of *Images* with different *Tags*. Typical name: `username/image_name`
* **Registry** holds a collection of *Repositories*.
* **Tag**s can distinguish same named *Images* in a *Repository*.


# Docker CLI

* `docker <command>`

|Command | Description                 |                       
|========|=============================|
| ps     | Show Containers             |
| images | Show Images                 |
| run    | Create and start container  |
| search | Search images in a Registry |
| pull   | Download Images             |
| rm     | Remove Container            |
| rmi    | Remove Image                |
| push   | Push Image to registry      |  


# Advanced Features
* Flexible and dynamic **port mapping**
* Container **linking**
* Sharing data via **volumes**


# Building Images

* **Comitting** container and promoting them to images.
	- `docker commit <container>` creates a new *Image* from the current container's state

* **Automated builds** with **Dockerfiles**
	- Dockerfile is a script containing build instructions for creating *Image*s


# `docker commit`

	docker run -t -i ubuntu bash
	docker commit <container> <image>
	docker tag <image> <user/image>
	docker push <user/image>

* Vorteile: Einfach, bekannt (git)
* Contra: Manueller Prozess, nicht reproduzierbar 


# Dockerfile

	FROM dockerfile/java
	
	MAINTAINER roland@jolokia.org
	
	EXPOSE 8080
	
	RUN wget http://archive.apache.org/tomcat-7/.. -O /tmp/c.tgz 
	RUN tar xzf /tmp/c.tgz -C /opt
	RUN rm /tmp/c.tgz
	CMD ["/opt/apache-tomcat-7/bin/catalina.sh", "run"]
	
	docker build . -t tomcat-7

* Vorteile: Einfach zu lernen, reproduzierbar, transparent
* Nachteile: Neue Skriptsprache


# Docker for Developers

* Better integration tests
* Streamlining the deployment pipelone


# Integration Testing
Goal: 

> Testing applications in a realistic context which is as close
> as possible to the production environment

* Application is deployed in the target server 
* Externals system are either simulated realistically or test systems are available


# Good Tests are ...

* **Robust** : Tests should either work or always fail with the same error if the code has not changed.
* **Standalone** : Minimal external requirements, ideally a build with integrations tests should be *self contained*.
* **Isolated**: Test should be able to run in parallel without e.g. port conflicts.
* **Fast**: Tests should be balzing fast to keep the feedback loop tight.


# External Testsystems
* Externally managed and configured (robust ?)
* Test systems must be installed, available and running (standalone ?)
* Parallel tests influence each other if using the same external test system (isolated ?)
* Oft quite slow due to network latency or because many are using the test system (fast ?)

But: Tests with external Systems are **realistic** !


# Docker to the rescue

* Perfect isolation for the System Under Test (**SUT**)
* Fast !
* Robust tests in a well defined environment
* No external requirements except a Docker installation


# Integration Docker in the Build

* Jenkins Pre- and Post-Actions for starting/stopping Docker
* Executing an external process from Ant or Maven
	- `exec` Task in Ant
		- `exec-maven-plugin` for Maven
* Dedicated `docker-maven-plugin`
* Also support for graddle available


# docker-maven-plugin NIH syndrom


# docker-maven-plugin

* https://github.com/rhuss/docker-maven-plugin
* Design Goals:
	- **Simple** configuration
	- **Automatic pulling** of required images
	- Support for **dynamic portmapping**
	- Maven **artifacts** and their dependencies should be available within the container
	- Support for **pushing** containers with artifacts to a container
	- Do it the **maven way**


# docker-maven-plugin Goals

* `docker:start` : Starts a container, bound by default to the `pre-integration-test` phase
* `docker:stop` : Stops one (or more) container, bound by default to the `post-integration-test` phase
* `docker:push` : Push a data image to a registry


# Simple Configuration

	<configuration>
	  <image>${image}</image>
	  <dataImage>demo/demo</dataImage>
	  <mergeData>false</mergeData>
	  <env>
	     <CATALINA_OPTS>-Xmx32m</CATALINA_OPTS>
	  </env>
	  <ports>
	     <port>jolokia.port:8080</port>
	  </ports>
	  <waitHttp>http://localhost:${jolokia.port}/jolokia</waitHttp>
	  <wait>10000</wait>
	  <assemblyDescriptor>src/main/assembly.xml</assemblyDescriptor>
	</configuration>


# Dynamic Port Mapping

	<configuration>
	  <ports>
	    <port>jolokia.port:8080</port>
	  </ports>
	  <systemPropertyVariables>
	     <jolokia.url>http://localhost:${jolokia.port}/jolokia</jolokia.url>
	  </systemPropertyVariables>
	</configuration>


# Data Image

* Docker pattern for images holding only data
* Linked into an 'active' container which working on the data
* Data is described by an *assembly descriptor* in the same format as used by the `maven-assembly-plugin`
   * Assembly can be also **merged** into the container-under-test


# Data Assembly

	<assembly>
	  <dependencySets>
	    <dependencySet>
	      <includes>
	        <include>org.jolokia:jolokia-war</include>
	      </includes>
	      <outputDirectory>.</outputDirectory>
	      <outputFileNameMapping>jolokia.war</outputFileNameMapping>
	    </dependencySet>
	    <dependencySet>
	      <includes>
	        <include>org.jolokia:jolokia-it-war</include>
	      </includes>
	      <outputDirectory>.</outputDirectory>
	      <outputFileNameMapping>jolokia-it.war</outputFileNameMapping>
	    </dependencySet>
	  </dependencySets>
	</assembly>

# Pushing images



 
