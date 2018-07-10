package com.example.huyvo.camel.routes.best.practices.processors;

import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SimpleProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(SimpleProcessor.class);

    public void printBody(Exchange exchange) {
        if (LOG.isInfoEnabled()) {
            LOG.info("==========BODY=========> {}", exchange.getIn().getBody());
        }
    }
}
