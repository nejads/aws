package se.kits.soroush.alexa.handlers;

import static com.amazon.ask.request.Predicates.intentName;

import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

public class StationIntentHandler implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("stationIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        String speechText = "Yes, there is a train station in Goteborg.";
        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("station", speechText)
                .build();
    }
}
