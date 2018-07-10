package com.camel.traning.route;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class FileRoute extends RouteBuilder {

  public void configure() {
    from("{{ftp.server}}&delay=3s&doneFileName=done.txt&fileName=Staff.csv")
            .log(LoggingLevel.INFO, "Transfer file: ${file:name}")
            .to("file:data/outbox");
  }
}
