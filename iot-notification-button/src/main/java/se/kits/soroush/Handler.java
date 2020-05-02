package se.kits.soroush;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;

public class Handler {

	private static final Logger LOG = LogManager.getLogger(Handler.class);

	public void handleRequest(Map<String, Object> input, Context context) {
		LOG.info("received: {}", input);

		Slacker.builder().build().send();

	}
}
