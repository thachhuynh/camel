package com.camel.traning;

import org.apache.camel.CamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class Application {
  private static Logger LOGGER = LoggerFactory.getLogger(Application.class);

  public static void main(String[] args) {
    ApplicationContext ctx = SpringApplication.run(Application.class, args);
    Environment env = ctx.getEnvironment();
    String port = env.getProperty("server.port");
    String[] activeProfiles = env.getActiveProfiles();



    LOGGER.info(
            "\n************************************************************************\n"
              + "\tListening on port: {}\n"
              + "\tActive profiles  : {}\n"
              + "************************************************************************",
            port,
            activeProfiles);

  }
}
