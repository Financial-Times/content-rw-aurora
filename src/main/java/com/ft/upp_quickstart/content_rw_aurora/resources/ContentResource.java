package com.ft.upp_quickstart.content_rw_aurora.resources;

import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ft.upp_quickstart.content_rw_aurora.service.ContentRWService;

@Path("/content")
public class ContentResource {
  private  static final String CHARSET_UTF_8 = ";charset=utf-8";
  
  private ContentRWService srv;
  
  public ContentResource(ContentRWService srv) {
    this.srv = srv;
  }
  
  @PUT
  @Path("/{uuid}")
  public void updateContent(@PathParam("uuid") String uuid, Map content) throws Exception {
    srv.writeContent(uuid, content);
  }
  
  @GET
  @Path("/{uuid}")
  @Produces(MediaType.APPLICATION_JSON + CHARSET_UTF_8)
  public Map getContent(@PathParam("uuid") String uuid) throws Exception {
    return srv.readContent(uuid);
  }
}
