package se.kits.soroush;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.webhook.Payload;
import com.github.seratch.jslack.api.webhook.WebhookResponse;

import lombok.Builder;

@Builder
public class Slacker implements Sender {
    private static final Logger LOG = LogManager.getLogger(Handler.class);

    @Override
    public void send() {
        // https://hooks.slack.com/services/T00000000/B00000000/XXXXXXXXXXXXXXXXXXXXXXXX
        String url = System.getenv("SLACK_WEBHOOK_URL");

        Payload payload = Payload.builder()
                .channel("#notification")
                .username("Potty Bot")
                .iconEmoji(":poop:")
                .text("Baby needs to go potty! :poop:")
                .build();

        Slack slack = Slack.getInstance();
        try {
            WebhookResponse response = slack.send(url, payload);
        } catch (IOException e) {
            LOG.error("Error: ", e);
        }
// response.code, response.message, response.body
    }
}
