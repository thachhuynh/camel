package com.example.huyvo.camel.routes.best.practices;

import com.example.huyvo.camel.routes.best.practices.processors.SimpleProcessor;
import com.example.huyvo.camel.routes.best.practices.routes.*;
import org.apache.camel.CamelContext;
import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
public class Application {
    public static void main(String[] args) throws Exception {
		ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(Application.class, args);
        CamelContext camelContext = configurableApplicationContext.getBean(CamelContext.class);
        SimpleProcessor simpleProcessor = configurableApplicationContext.getBean(SimpleProcessor.class);

        /**
         * Direct route example
         */
//        camelContext.addRoutes(new DirectRoute(simpleProcessor));
//        camelContext.addRoutes(new TriggerRoute(DirectRoute.DIRECT_URI));

        /**
         * Seda route example synchronous process
         */
//        camelContext.addRoutes(new SedaRoute(simpleProcessor));
//        camelContext.addRoutes(new TriggerRoute(SedaRoute.SEDA_URI));

        /**
         * Seda route example asynchronous process
         */
//        camelContext.addRoutes(new SedaRoute(simpleProcessor));
//        camelContext.addRoutes(new TriggerRoute(SedaRoute.SEDA_URI, ExchangePattern.InOnly));

        /**
         * Read file and parse whole file content
         */
//        camelContext.addRoutes(new ReadFileParseAllContentToMemoryRoute());

        /**
         * Read file and parse line by line
         */
//        camelContext.addRoutes(new ReadFileParseContentLineByLineRoute());

        /**
         * Read file and parse line by line, group and process all of them
         */
//        camelContext.addRoutes(new ReadFileParseContentLineByLineWithAggregatorRoute());
        // example for JsonWriter (GSON) to streaming write data to file
        // https://static.javadoc.io/com.google.code.gson/gson/2.6.2/com/google/gson/stream/JsonWriter.html

        /**
         * Exception global and route scopes
         */
//        camelContext.addRoutes(new ExceptionHandlerRoute());
    }

    /**
     * just a simple timer route to send a message to destinationUri
     */
    private static class TriggerRoute extends RouteBuilder {
        private static final String HEADER_COUNT = "HEADER_COUNT";
        private String destinationUri;
        private ExchangePattern exchangePattern;

        public TriggerRoute(String destinationUri) {
            this.destinationUri = destinationUri;
            this.exchangePattern = ExchangePattern.InOut;
        }

        public TriggerRoute(String destinationUri, ExchangePattern exchangePattern) {
            this.destinationUri = destinationUri;
            this.exchangePattern = exchangePattern;
        }

        @Override
        public void configure() throws Exception {
            AtomicInteger atomicInteger = new AtomicInteger(0);
            /**
             * the timer run every 1 second
             */
            from("timer:TriggerRoute?period=1000")
                    .routeId("TriggerRoute")
                    // every period sending 2 messages
                    .process(exchange -> exchange.getIn().setHeader(HEADER_COUNT, atomicInteger.incrementAndGet()))
                    .setBody(header(HEADER_COUNT))
                    .log(">>>> Start trigger number #${header.HEADER_COUNT}")
                    .setExchangePattern(exchangePattern)

                    // send exchange message to other route
                    /*
                     * ExchangePattern.InOnly == .wireTap(...)
                     */
                    .to(destinationUri)

                    // get back message and end the timer route
                    .log("<<<< End trigger number #${header.HEADER_COUNT} \n")
                    // end splitter
                    .end();
        }
    }
}
