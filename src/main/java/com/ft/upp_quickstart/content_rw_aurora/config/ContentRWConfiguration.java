package com.ft.upp_quickstart.content_rw_aurora.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ft.platform.dropwizard.AppInfo;
import com.ft.platform.dropwizard.ConfigWithAppInfo;
import com.ft.platform.dropwizard.ConfigWithGTG;
import com.ft.platform.dropwizard.GTGConfig;

import io.dropwizard.Configuration;

public class ContentRWConfiguration extends Configuration implements ConfigWithGTG, ConfigWithAppInfo {
  @JsonProperty("appInfo")
  private AppInfo appInfo;
  @JsonProperty("db")
  private DbConfig db;
  
  @Override
  public AppInfo getAppInfo() {
    return appInfo;
  }

  @Override
  public GTGConfig getGtg() {
    // TODO Auto-generated method stub
    return null;
  }
  
  public DbConfig getDb() {
    return db;
  }
}
