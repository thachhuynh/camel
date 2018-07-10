package com.example.huyvo.camel.routes.best.practices.routes;

import com.example.huyvo.camel.routes.best.practices.processors.SimpleProcessor;
import org.apache.camel.builder.RouteBuilder;

public class SedaRoute extends RouteBuilder {
    public static final String SEDA_URI = "seda:SedaRouteSimple";

    private SimpleProcessor simpleProcessor;

    public SedaRoute(SimpleProcessor simpleProcessor) {
        this.simpleProcessor = simpleProcessor;
    }

    @Override
    public void configure() throws Exception {
        /**
         * exchange pattern sent to SEDA component will affect the behaviours.
         *
         * 2 type: Exchange.InOnly & Exchange.InOut
         */
        from(SEDA_URI + "?concurrentConsumers=2")
                .routeId("SedaRouteSimple")
                .setBody(simple("Start Hello world #${header.HEADER_COUNT}"))
                .process(simpleProcessor::printBody)
                .delay(2_000)
                .setBody(simple("End Hello world #${header.HEADER_COUNT}"))
                .process(simpleProcessor::printBody)
        ;
    }
}
