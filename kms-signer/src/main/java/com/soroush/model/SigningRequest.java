package com.soroush.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.Map;

@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({"body", "resource", "queryStringParameters",
    "headers", "pathParameters", "httpMethod", "stageVariables", "path"})
public class SigningRequest {

  @JsonProperty("body")
  private String body;
  @JsonProperty("resource")
  private String resource;
  @JsonProperty("queryStringParameters")
  private Map<String, Object> queryStringParameters;
  @JsonProperty("headers")
  private Map<String, Object> headers;
  @JsonProperty("pathParameters")
  private Map<String, Object> pathParameters;
  @JsonProperty("httpMethod")
  private String httpMethod;
  @JsonProperty("stageVariables")
  private Map<String, Object> stageVariables;
  @JsonProperty("path")
  private String path;

  public SigningRequest() {
  }

  @JsonProperty("body")
  public String getBody() {
    return this.body;
  }

  @JsonProperty("body")
  public void setBody(String body) {
    this.body = body;
  }

  @JsonProperty("resource")
  public String getResource() {
    return this.resource;
  }

  @JsonProperty("resource")
  public void setResource(String resource) {
    this.resource = resource;
  }

  @JsonProperty("queryStringParameters")
  public Map<String, Object> getQueryStringParameters() {
    return this.queryStringParameters;
  }

  @JsonProperty("queryStringParameters")
  public void setQueryStringParameters(Map<String, Object> queryStringParameters) {
    this.queryStringParameters = queryStringParameters;
  }

  @JsonProperty("headers")
  public Map<String, Object> getHeaders() {
    return this.headers;
  }

  @JsonProperty("headers")
  public void setHeaders(Map<String, Object> headers) {
    this.headers = headers;
  }

  @JsonProperty("pathParameters")
  public Map<String, Object> getPathParameters() {
    return this.pathParameters;
  }

  @JsonProperty("pathParameters")
  public void setPathParameters(Map<String, Object> pathParameters) {
    this.pathParameters = pathParameters;
  }

  @JsonProperty("httpMethod")
  public String getHttpMethod() {
    return this.httpMethod;
  }

  @JsonProperty("httpMethod")
  public void setHttpMethod(String httpMethod) {
    this.httpMethod = httpMethod;
  }

  @JsonProperty("stageVariables")
  public Map<String, Object> getStageVariables() {
    return this.stageVariables;
  }

  @JsonProperty("stageVariables")
  public void setStageVariables(Map<String, Object> stageVariables) {
    this.stageVariables = stageVariables;
  }

  @JsonProperty("path")
  public String getPath() {
    return this.path;
  }

  @JsonProperty("path")
  public void setPath(String path) {
    this.path = path;
  }

}
