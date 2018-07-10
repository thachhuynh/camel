package com.camel.traning.route;

import com.camel.traning.domain.StaffCsvRecord;
import com.camel.traning.processor.CsvRecordProcessor;
import com.camel.traning.strategy.ListAggregationStrategy;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.spi.DataFormat;

import java.util.List;
import java.util.Map;

public class CsvParserRoute extends RouteBuilder {

  DataFormat bindy = new BindyCsvDataFormat(StaffCsvRecord.class);

  @Override
  public void configure() throws Exception {
    from("file:data/outbox?delay=10s&fileName=Staff.csv&idempotentKey=${file:name}-${file:modified}")
            .log(LoggingLevel.INFO, "Processing file: ${file:name}")
            .split().tokenize("\r\n|\n")
            .streaming()
              .unmarshal(bindy)
              .aggregate(header(Exchange.FILE_NAME), new ListAggregationStrategy())
              .completionSize(2)
              .completionPredicate(exchangeProperty(Exchange.SPLIT_COMPLETE))
              .completionTimeout(10_000)

                // DO whatever we need with a batch of data
                .process(new CsvRecordProcessor())
              // End aggregating
              .end()
            .end();
  }
}
