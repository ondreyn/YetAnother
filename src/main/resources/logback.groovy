import ch.qos.logback.classic.encoder.PatternLayoutEncoder

appender("STDOUT", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"
    }
}

appender("FILE", FileAppender) {
    file = "logs/logfile.log"
    encoder(PatternLayoutEncoder) {
        pattern = "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"
        outputPatternAsHeader = true
    }
}
root(INFO, ["FILE", "STDOUT"])
//root(INFO, ["STDOUT", "FILE"])
//root(ERROR, ["STDOUT", "FILE"])
//root(ERROR, ["FILE"])