package com.soroush.handler;

import java.util.HashMap;
import java.util.Map;

public class HandlerCommons {

  static Map<String, String> getHeaders() {
    final HashMap<String, String> headers = new HashMap<>();
    headers.put("X-Powered-By", "AWS Lambda & Serverless");
    headers.put("Content-Type", "application/json");
    return headers;
  }

}
