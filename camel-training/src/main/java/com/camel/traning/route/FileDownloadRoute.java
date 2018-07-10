package com.camel.traning.route;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

public class FileDownloadRoute extends RouteBuilder {

  public void configure() {

    from("{{ftp.server}}&delay=3s&doneFileName=done.txt&fileName=Staff.csv&idempotentKey=${file:name}-${file:modified}")
            .log(LoggingLevel.INFO, "Downloading file: ${file:name}")
            .to("file:data/outbox");
  }
}
