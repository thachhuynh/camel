package com.example.huyvo.camel.routes.best.practices.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.CsvDataFormat;

public class ReadFileParseAllContentToMemoryRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        CsvDataFormat csvDataFormat = new CsvDataFormat(",");

        from("file:samples?fileName=ExampleCsvFile.CSV&noop=true&idempotentKey=${file:name}-${file:modified}")
                .routeId("ReadFileAllContentToMemory")
                // unmarshall all content file (in the exchange body) to a List of data
                .unmarshal(csvDataFormat)
                .log("Content parsed size = ${body.size} ====> \n ${body}")
                .log("End unmarshalling csv file ${file:name}");
    }
}
