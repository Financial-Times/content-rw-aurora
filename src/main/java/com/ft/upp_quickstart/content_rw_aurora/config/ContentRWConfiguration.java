package com.ft.upp_quickstart.content_rw_aurora.config;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;

public class ContentRWConfiguration extends Configuration {
  @JsonProperty("db")
  private DbConfig db;
  
  public DbConfig getDb() {
    return db;
  }
}
