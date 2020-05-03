package com.soroush;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.soroush.model.ApiGatewayResponse;
import com.soroush.model.SigningRequest;
import com.soroush.model.SigningRequestModel;
import com.soroush.model.SigningResponse;
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
    LOG.debug("EVENT: {}", gson.toJson(request));

    String messageToBeSigned = validateAndExtractMessage(request);
    LOG.debug("Message to be signed is {}", messageToBeSigned);
    return messageToBeSigned == null ?
        errorResponse() :
        successfulResponse(messageToBeSigned);
  }

  private String validateAndExtractMessage(final SigningRequest request) {
    final SigningRequestModel body = gson.fromJson(request.getBody(), SigningRequestModel.class);
    LOG.debug("Body {}", request.getBody());
    LOG.debug("ParseBody {}", body);
    return body == null || body.getMessage() == null ? null : body.getMessage();
  }

  private static ApiGatewayResponse errorResponse() {
    LOG.debug("Are you here?");
    return ApiGatewayResponse.builder()
        .setStatusCode(400)
        .setRawBody("Request is not valid")
        .setHeaders(getHeaders())
        .build();
  }

  private static ApiGatewayResponse successfulResponse(String message) {
    SigningResponse response = new SigningResponse("This is the signed data");
    return ApiGatewayResponse.builder()
        .setStatusCode(200)
        .setObjectBody(response)
        .setHeaders(getHeaders())
        .build();
  }

  private static Map<String, String> getHeaders() {
    final HashMap<String, String> headers = new HashMap<>();
    headers.put("X-Powered-By", "AWS Lambda & Serverless");
    headers.put("Content-Type", "application/json");
    return headers;
  }
}
