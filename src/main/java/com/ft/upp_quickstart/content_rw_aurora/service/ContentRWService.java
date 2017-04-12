package com.ft.upp_quickstart.content_rw_aurora.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.dropwizard.lifecycle.Managed;

public class ContentRWService implements Managed {
  private final String dbUrl;
  private final String dbUser;
  private final String dbPassword;
  private final ObjectMapper mapper;
  private Connection conn;
  
  public ContentRWService(String dbUrl, String dbUser, String dbPassword, ObjectMapper mapper) {
    this.dbUrl = dbUrl;
    this.dbUser = dbUser;
    this.dbPassword = dbPassword;
    this.mapper = mapper;
  }
  
  @Override
  public void start() throws Exception {
      conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
  }

  @Override
  public void stop() throws Exception {
    conn.close();
  }

  public void writeContent(String uuid, Object content) throws Exception {
    ByteArrayOutputStream raw = new ByteArrayOutputStream();
    mapper.writeValue(raw, content);
    
    try (PreparedStatement insert = conn.prepareStatement("INSERT INTO content (uuid, content) VALUES (?, ?)")) {
      insert.setString(1, uuid);
      insert.setBinaryStream(2, new ByteArrayInputStream(raw.toByteArray()));
      
      insert.executeUpdate();
    } catch (SQLIntegrityConstraintViolationException e) {
      try (PreparedStatement update = conn.prepareStatement("UPDATE content SET content = ? WHERE uuid = ?")) {
        update.setBinaryStream(1, new ByteArrayInputStream(raw.toByteArray()));
        update.setString(2, uuid);
        update.executeUpdate();
      }
    }
  }
  
  public Map readContent(String uuid) throws Exception {
    Map content = null;
    
    ResultSet rs = null;
    try (PreparedStatement pstmt = conn.prepareStatement("SELECT content FROM content WHERE uuid = ?")) {
      pstmt.setString(1, uuid);
      
      rs = pstmt.executeQuery();
      if (rs.next()) {
        content = mapper.readValue(rs.getBinaryStream(1), HashMap.class);
      }
    } finally {
      rs.close();
    }
    
    return content;
  }
}
