package com.camel.traning.processor;

import com.camel.traning.domain.StaffCsvRecord;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.List;

public class CsvRecordProcessor implements Processor {

  @Override
  public void process(Exchange exchange) throws Exception {
    List<StaffCsvRecord> staffCsvRecords = (List<StaffCsvRecord>) exchange.getIn().getBody();
    staffCsvRecords.forEach(staffCsvRecord -> {
      System.out.println(staffCsvRecord.getAgencyId() + " " + staffCsvRecord.getStaffId());
    });
  }
}
