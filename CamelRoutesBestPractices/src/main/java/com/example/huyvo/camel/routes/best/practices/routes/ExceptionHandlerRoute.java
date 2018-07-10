package com.example.huyvo.camel.routes.best.practices.routes;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

public class ExceptionHandlerRoute extends RouteBuilder {
    private static final String ENDPOINT_A = "direct:RouteWithRouteException";
    private static final String ENDPOINT_B = "direct:RouteWithoutRouteException";

    @Override
    public void configure() throws Exception {

        /*
        GLOBAL exception, every route will inherit this exception handler
         */
        onException(Exception.class)
                .handled(true)
                .log(LoggingLevel.ERROR, "This is <<GLOBAL>> exception..... ${exception.message} - ${exception.stacktrace}");


        from("timer:TestExceptionTrigger?repeatCount=1")
                .routeId("TestExceptionTrigger")
                .to(ENDPOINT_A);


        from(ENDPOINT_A)
                .routeId("RouteWithRouteException")

                /*
                ROUTE exception, every route will inherit this exception handler
                 */
                .onException(Exception.class)
                    .handled(true)
                    .log(LoggingLevel.ERROR, "This is <<ROUTE>> ${routeId} exception")
                .end()

                .log("Start route WITH route exception!!!!")
                .throwException(new RuntimeException("My Exception 1 test!"))
                .log("End route WITH route exception!!!");

        from(ENDPOINT_B)
                .routeId("RouteWithoutRouteException")
                .log("Start route WITHOUT route exception!!!!")
                .throwException(new RuntimeException("My Exception 2 test!"))
                .log("End route WITHOUT route exception!!!");
    }
}
