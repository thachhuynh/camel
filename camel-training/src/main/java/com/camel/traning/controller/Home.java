package com.camel.traning.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Home {

  @GetMapping("/api/test")
  public String test() {
    return "OK";
  }
}
