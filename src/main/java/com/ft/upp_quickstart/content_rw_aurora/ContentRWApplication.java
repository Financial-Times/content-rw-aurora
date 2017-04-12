package com.ft.upp_quickstart.content_rw_aurora;

import com.ft.upp_quickstart.content_rw_aurora.config.ContentRWConfiguration;
import com.ft.upp_quickstart.content_rw_aurora.config.DbConfig;
import com.ft.upp_quickstart.content_rw_aurora.resources.ContentResource;
import com.ft.upp_quickstart.content_rw_aurora.service.ContentRWService;
import com.ft.platform.dropwizard.AdvancedHealthCheckBundle;
import com.ft.platform.dropwizard.DefaultGoodToGoChecker;
import com.ft.platform.dropwizard.GoodToGoBundle;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ContentRWApplication extends Application<ContentRWConfiguration> {

  public static void main(final String[] args) throws Exception {
      new ContentRWApplication().run(args);
  }

  @Override
  public void initialize(Bootstrap<ContentRWConfiguration> bootstrap) {
      bootstrap.addBundle(new AdvancedHealthCheckBundle());
      bootstrap.addBundle(new GoodToGoBundle(new DefaultGoodToGoChecker()));
      
  }

  @Override
  public void run(final ContentRWConfiguration configuration, final Environment environment) throws Exception {
    DbConfig db = configuration.getDb();
    ContentRWService service = new ContentRWService(db.getUrl(), db.getUsername(), db.getPassword(), environment.getObjectMapper());
    environment.lifecycle().manage(service);
    
    ContentResource resource = new ContentResource(service);
    environment.jersey().register(resource);
  }
}
