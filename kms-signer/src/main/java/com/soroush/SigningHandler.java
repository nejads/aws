package com.soroush;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SigningHandler implements RequestHandler<SigningRequest, ApiGatewayResponse> {

  private static final Logger LOG = LogManager.getLogger(SigningHandler.class);
  Gson gson = new GsonBuilder().setPrettyPrinting().create();

  @Override
  public ApiGatewayResponse handleRequest(SigningRequest request, Context context) {
    // log execution details
    LOG.debug("CONTEXT: {}", gson.toJson(context));
    // process request
    LOG.debug("EVENT: {}", gson.toJson(request.getBody()));

    SigningResponse response = new SigningResponse("This is the signed data");
    return ApiGatewayResponse.builder()
        .setStatusCode(200)
        .setObjectBody(response)
        .setHeaders(getHeaders())
        .build();
  }

  private Map<String, String> getHeaders() {
    final HashMap<String, String> headers = new HashMap<>();
    headers.put("X-Powered-By", "AWS Lambda & Serverless");
    headers.put("Content-Type", "application/json");
    return headers;
  }
}
