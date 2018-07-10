package com.example.huyvo.camel.routes.best.practices.routes;

import com.example.huyvo.camel.routes.best.practices.processors.SimpleProcessor;
import org.apache.camel.builder.RouteBuilder;

public class DirectRoute extends RouteBuilder {
    public static final String DIRECT_URI = "direct:DirectRouteSimple";

    private SimpleProcessor simpleProcessor;

    public DirectRoute(SimpleProcessor simpleProcessor) {
        this.simpleProcessor = simpleProcessor;
    }

    @Override
    public void configure() throws Exception {
        from(DIRECT_URI)
                .routeId("DirectRouteSimpleId")
                .setBody(simple("Start Hello world #${header.HEADER_COUNT}"))
                .bean("simpleProcessor", "printBody")
                .delay(2_000)
                .setBody(simple("End Hello world #${header.HEADER_COUNT}"))
                .process(simpleProcessor::printBody);
    }
}
