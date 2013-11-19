Message streaming components
============================

Message streaming is split into 2 components: .NET and Java. Both components can work as message emitters or receivers.
This repository consist of the following projects:
- streamer-java: message streaming for Java
- streamer-net: message streming for .NET
- messenger-monitor-webapp: simple web application for monitoring streaming components (works only with Java component).

Building instructions
---------------------

The `streamer-java` uses maven for dependency management and build requires issuing the following commands:
    
    git clone git://github.com/project-first/messaging.git
    cd messaging\streamer-java
    mvn clean install
    
After successful build, the compiled JAR file is located in `streamer-java\target` folder. Please note that you will find 2 versions: one JAR with dependencies (`messenger-nj-0.0.3-SNAPSHOT-jar-with-dependencies.jar`) and the other without (`messenger-nj-0.0.3-SNAPSHOT.jar`). It is advised to use the latter (without dependencies).
As this component is typically included as a library, we recommend to check the messaging API. The documentation of streamer API is described in FIRST D7.1 Integration Infrastructure Release, 2012.



