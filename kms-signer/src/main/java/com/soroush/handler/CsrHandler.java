package com.soroush.handler;

import static com.soroush.handler.HandlerCommons.getHeaders;
import static org.apache.http.HttpStatus.SC_OK;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.soroush.CsrCreator;
import com.soroush.model.ApiGatewayResponse;
import java.util.Map;

public class CsrHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

  @Override
  public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
    return ApiGatewayResponse.builder()
        .setStatusCode(SC_OK)
        .setRawBody(new CsrCreator().create())
        .setHeaders(getHeaders())
        .build();
  }
}
