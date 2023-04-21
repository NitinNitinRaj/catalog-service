package com.polarbookshop.catalogservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

  public static final String HOME_PATH = "/";

  @GetMapping(HOME_PATH)
  public String getGreeting() {
    return "Welcome to the catalog!";
  }
}
