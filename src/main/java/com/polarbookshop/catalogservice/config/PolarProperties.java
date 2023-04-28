package com.polarbookshop.catalogservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "polar")
public class PolarProperties {

  /**
   * A welcome message to the user
   */
  private String greetings;

  public String getGreetings() {
    return greetings;
  }

  public void setGreetings(String greetings) {
    this.greetings = greetings;
  }
}
