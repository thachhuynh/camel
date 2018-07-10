package com.example.huyvo.camel.routes.best.practices.routes;

import com.example.huyvo.camel.routes.best.practices.strategy.ListAggregationStrategy;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.CsvDataFormat;

public class ReadFileParseContentLineByLineWithAggregatorRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        CsvDataFormat csvDataFormat = new CsvDataFormat(",");

        from("file:samples?fileName=ExampleCsvFile.CSV&noop=true&idempotentKey=${file:name}-${file:modified}")
                .routeId("ReadFileParseContentLineByLineWithAggregatorRoute")
                // Split line by line, with token line break
                // Split can be used with Array/Collection/Iterator
                .split(body().regexTokenize("\r\n|\n")).streaming()
                    .unmarshal(csvDataFormat)
                    .setBody(simple("${body[0]}"))

                    .aggregate(header(Exchange.FILE_NAME), new ListAggregationStrategy())
                        .completionSize(10)
                        .completionPredicate(exchangeProperty(Exchange.SPLIT_COMPLETE))
                        .completionTimeout(10_000)

                            // DO whatever we need with a batch of data
                            .log("Content parsed size = ${body.size} ====> \n ${body}")
                    // End aggregating
                    .end()
                // End splitting
                .end()
                .log("End unmarshalling csv file ${file:name}");
    }
}
