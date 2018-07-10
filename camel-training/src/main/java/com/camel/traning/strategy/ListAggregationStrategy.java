package com.camel.traning.strategy;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.util.ArrayList;
import java.util.List;

public class ListAggregationStrategy implements AggregationStrategy {

    /**
     * Aggregate results into a List
     */
    @Override
    @SuppressWarnings("unchecked")
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        List<Object> resultList;
        Object record = newExchange.getIn().getBody();

        if (oldExchange == null) {
            resultList = new ArrayList<>();
            resultList.add(record);
            newExchange.getIn().setBody(resultList);
            return newExchange;
        } else {
            oldExchange.setProperty(Exchange.SPLIT_COMPLETE, newExchange.getProperty(Exchange.SPLIT_COMPLETE));
            oldExchange.setProperty(Exchange.EXCEPTION_CAUGHT, newExchange.getProperty(Exchange.EXCEPTION_CAUGHT));
            resultList = oldExchange.getIn().getBody(List.class);
            resultList.add(record);
            return oldExchange;
        }
    }
}
