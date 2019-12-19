# Logback Clojure Filter

<!-- badges -->
[![CircleCI](https://circleci.com/gh/lambdaisland/{project}.svg?style=svg)](https://circleci.com/gh/lambdaisland/{project}) [![cljdoc badge](https://cljdoc.org/badge/lambdaisland/{project})](https://cljdoc.org/d/lambdaisland/{project}) [![Clojars Project](https://img.shields.io/clojars/v/lambdaisland/{project}.svg)](https://clojars.org/lambdaisland/{project})
<!-- /badges -->

Use Clojure expressions to filter messages in a Logback appender.

`logback.xml:`

``` xml
  <appender name="CUSTOM" class="ch.qos.logback.core.FileAppender">
    <file>/tmp/custom.log</file>
    <filter class="lambdaisland.logback.ClojureFilter">
      <expression>
        (= "my.ns" logger-name)
      </expression>
    </filter>
    <encoder>
      <pattern>%msg%n</pattern>
    </encoder>
  </appender>
```

Exposes the following local variables

- `event`: the full ([`ILoggingEvent`](https://github.com/qos-ch/logback/blob/master/logback-classic/src/main/java/ch/qos/logback/classic/spi/ILoggingEvent.java))
- `logger-name`: the logger name (String)
- `level`: the log level ([Level](https://github.com/qos-ch/logback/blob/master/logback-classic/src/main/java/ch/qos/logback/classic/Level.java))
- `message`: the log message (String)
- `marker`: slf4j [Marker](http://www.slf4j.org/api/org/slf4j/Marker.html)
- `timestamp`: unix timestamp (long)

If the expression returns truthy the message is logged, if it returns falsey or throws, the message is filtered out.

If your code isn't working as expected you can check `lambdaisland.logback.clojure-filter/last-error`, an atom which will contain the most recently thrown error.

## License

Copyright &copy; 2019 Arne Brasseur

Licensed under the term of the Mozilla Public License 2.0, see LICENSE.
