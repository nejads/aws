package se.kits.soroush.alexa;

import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.Skills;

import se.kits.soroush.alexa.handlers.StationIntentHandler;
import se.kits.soroush.alexa.handlers.FallbackIntentHandler;
import se.kits.soroush.alexa.handlers.HelpIntentHandler;
import se.kits.soroush.alexa.handlers.LaunchRequestHandler;
import se.kits.soroush.alexa.handlers.SessionEndedRequestHandler;
import se.kits.soroush.alexa.handlers.StopOrCancelIntentHandler;

public class TrainStationsStreamHandler extends SkillStreamHandler {

    private static Skill getSkill() {
        return Skills.standard()
                .addRequestHandlers(
                        new FallbackIntentHandler(),
                        new HelpIntentHandler(),
                        new LaunchRequestHandler(),
                        new SessionEndedRequestHandler(),
                        new StopOrCancelIntentHandler(),
                        new StationIntentHandler())
                .build();
    }

    public TrainStationsStreamHandler() {
        super(getSkill());
    }
}
