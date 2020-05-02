package com.serverless;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.apache.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

public class Handler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

	private static final Logger LOG = Logger.getLogger(Handler.class);

	@Override
	public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
		LOG.info("received: " + input);
		return ApiGatewayResponse.builder()
				.setStatusCode(200)
				.setRawBody("Are you there? " + getCloudMessage())
				.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & serverless"))
				.build();
	}

	private String getCloudMessage() {

		JsonParser parser = new JsonParser();
		JsonObject jsonObject = null;
		final String url = ".../message/produce"; //URL of your producer which deployed on AWS lambdas.

		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder()
				.url(url)
				.build();

		try {
			com.squareup.okhttp.Response response = client.newCall(request).execute();
			final String responseMessage = response.body().string();
			jsonObject = parser.parse(responseMessage).getAsJsonObject();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonObject.get("message").getAsString();
	}
}
