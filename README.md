YetAnother ticket system
========================

Thank you for choosing YetAnother.

Build Status

Note that YetAnother 1.0 is not the Final release, watch for updates.

INSTALLATION

Please make sure the release file is unpacked under a Web-accessible directory.
You shall see the following files and directories: 

| Entry | Description |
| ------ | ------ |
| logs/ | logs (would be created after 1st run) |
| target/ | deployed app's files (would be created after 1st run) |
| src/ | source files |
| README.md | this file |
| start.sh | Bash start up scenario |
| start.cmd | Windows NT start up scenario |
| pom.xml | maven pom file | 

REQUIREMENTS

+ JDK 1.8 and above  
+ Apache Maven 3.3.9 and above
+ Active internet connection for Bootstrap, AngularJS
+ Windows: system variables %JAVA_HOME%, %MAVEN_HOME% set correctly
+ Linux: JAVA and MAVEN binaries must be seen in $PATH

YetAnother has been tested with Jetty 9.4.7 HTTP server on Windows (NT 10.0
.10240 x64) and 
Linux (4.1.38 x86-64)
operating systems.

QUICK START

YetAnother comes with a run-file called "start" that can run the Jetty HTTP server
via a maven plugin.

On command line, type in the following commands:

    $ cd /Path/to/YetAnother                        (Linux)
    $ chmod uo+r start.sh                           (Linux)    
    cd \Path\to\YetAnother                          (Windows)

    $ sh ./start.sh                                 (Linux)
    start.cmd                                       (Windows)
The new YetAnother application will be created at "YetAnotherPath/target". 
Default context root is "/". You can access it with the following URL:

    http://localhost:8080/

