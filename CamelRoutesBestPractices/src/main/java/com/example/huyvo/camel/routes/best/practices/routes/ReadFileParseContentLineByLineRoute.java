package com.example.huyvo.camel.routes.best.practices.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.CsvDataFormat;

public class ReadFileParseContentLineByLineRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        CsvDataFormat csvDataFormat = new CsvDataFormat(",");

        from("file:samples?fileName=ExampleCsvFile.CSV&noop=true&idempotentKey=${file:name}-${file:modified}")
                .routeId("ReadFileParseContentLineByLineRoute")
                // Split line by line, with token line break
                .split(body().regexTokenize("\r\n|\n")).streaming()
                    .unmarshal(csvDataFormat)
                    .log("Content parsed size = ${body.size} ====> \n ${body}")
                .end()
                .log("End unmarshalling csv file ${file:name}");
    }
}
