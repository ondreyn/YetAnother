import ch.qos.logback.classic.encoder.PatternLayoutEncoder

/*appender("FILE", FileAppender) {
    file = "logs/logfile.log"
    encoder(PatternLayoutEncoder) {
        pattern = "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"
        outputPatternAsHeader = true
    }
}*/

appender("STDOUT", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"
    }
}

//root(INFO, ["FILE"])
root(INFO, ["STDOUT"])
//root(INFO, ["STDOUT", "FILE"])