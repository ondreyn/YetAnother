#! /bin/bash
if [ ! -f pom.xml ]
then
    echo "pom.xml not found!! exit"
    exit 1
fi

if [ ! -z "$JAVA_HOME" ]
then
    JAVA=`which java`
else 
    JAVA="$JAVA_HOME/bin/java"
fi

if [ ! -x "$JAVA" ] 
then
    echo "Check out your JDK installation!" >&2
    exit 1
fi

MAVEN=`which mvn`
if [ ! -x "$MAVEN" ] 
then
    echo "Check out your Maven installation!" >&2
    exit 1
fi

echo "type 'run' if you want to run the app, type 'test' to run tests:"
read action

if [ $action == 'run' ]
then
    exec "$MAVEN" jetty:run
fi

if [ $action == 'test' ]
then
    exec "$MAVEN" test
fi