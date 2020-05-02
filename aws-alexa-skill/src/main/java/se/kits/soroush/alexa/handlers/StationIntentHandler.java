package se.kits.soroush.alexa.handlers;

import static com.amazon.ask.request.Predicates.intentName;

import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.DialogState;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;

import se.kits.soroush.alexa.client.StationClient;

public class StationIntentHandler implements RequestHandler {

    private static final Logger log = LoggerFactory.getLogger(StationIntentHandler.class);
    private static final String CITY_NAME = "myCity";
    private StationClient client = new StationClient();

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("stationIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        IntentRequest request = (IntentRequest) input.getRequestEnvelope().getRequest();

        //Handel different state of an intent
        if (DialogState.STARTED == request.getDialogState()) {
            log.info("Started");
            return input.getResponseBuilder()
                    .addDelegateDirective(request.getIntent())
                    .build();
        }

        if (DialogState.IN_PROGRESS == request.getDialogState()){
            log.info("In progress");
            return input.getResponseBuilder()
                    .addDelegateDirective(null)
                    .build();
        }

        String speechText = "";

        // Get the vehicle number slot from the list of slots.
        Map<String, Slot> slots = request.getIntent().getSlots();
        String city = getCityWithoutSwedishAlpa(slots);
        log.info("Received request for city {}", city);

        try {
            boolean result = client.getStations().stream()
                    .anyMatch(station -> station.getSlug().matches(city.toLowerCase()+".*"));

            if (result) {
                speechText = "Yes, there is a train station in " + city;
            } else {
                speechText = "No, there is not a train station in " + city;
            }


        } catch (Exception e) {
            speechText = "Sorry, I don't know that one.";
        }

        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("station", speechText)
                .build();
    }

    private String getCityWithoutSwedishAlpa(Map<String, Slot> slots) {
        return slots.get(CITY_NAME).getValue()
                .replace("Ö", "O")
                .replace("ö", "o")
                .replace("Å", "A")
                .replace("å", "a")
                .replace("Ä", "A")
                .replace("ä", "a");
    }
}
