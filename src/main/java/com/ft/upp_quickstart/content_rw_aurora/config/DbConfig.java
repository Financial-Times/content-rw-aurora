package com.ft.upp_quickstart.content_rw_aurora.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DbConfig {
  @JsonProperty("url")
  private String url;
  @JsonProperty("username")
  private String user;
  @JsonProperty("password")
  private String password;
  
  public String getUrl() {
    return url;
  }
  
  public String getUsername() {
    return user;
  }
  
  public String getPassword() {
    return password;
  }
}
