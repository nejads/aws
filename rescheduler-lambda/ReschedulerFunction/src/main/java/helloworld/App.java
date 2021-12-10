package helloworld;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import com.amazonaws.services.cloudwatchevents.AmazonCloudWatchEvents;
import com.amazonaws.services.cloudwatchevents.AmazonCloudWatchEventsClientBuilder;
import com.amazonaws.services.cloudwatchevents.model.DescribeRuleRequest;
import com.amazonaws.services.cloudwatchevents.model.DescribeRuleResult;
import com.amazonaws.services.cloudwatchevents.model.ListRuleNamesByTargetRequest;
import com.amazonaws.services.cloudwatchevents.model.ListRuleNamesByTargetResult;
import com.amazonaws.services.cloudwatchevents.model.PutRuleRequest;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class App implements RequestHandler<Void, Void> {

    private AmazonCloudWatchEvents cloudWatchEvents;

    public App()  {
        String region = System.getenv("AWS_REGION");
        cloudWatchEvents = AmazonCloudWatchEventsClientBuilder.standard().withRegion(region).build();
    }
    @Override
    public Void handleRequest(Void input, Context context) {
        try {
            System.out.println("Processing event");
        } catch (Exception e) {
            System.out.println("Error Handling");
        } finally {
            reschedule(context);
        }
        return null;
    }

    private void reschedule(Context context) {
        ListRuleNamesByTargetResult rules = cloudWatchEvents.listRuleNamesByTarget(
                new ListRuleNamesByTargetRequest().withTargetArn(context.getInvokedFunctionArn())
        );
        for (String rule : rules.getRuleNames()) {
            DescribeRuleResult ruleDescription = cloudWatchEvents.describeRule(new DescribeRuleRequest().withName(rule));
            if (ruleDescription.getScheduleExpression().matches("cron[(]\\d+ [*] [*] [*] [?] [*][)]") || ruleDescription.getScheduleExpression().startsWith("rate")) {
                int randomNum = ThreadLocalRandom.current().nextInt(30, 90);
                int nextMinute = (LocalDateTime.now().getMinute()+randomNum)%60;
                cloudWatchEvents.putRule(new PutRuleRequest().withName(rule).withScheduleExpression("cron("+nextMinute+" * * * ? *)"));
            }
        }
    }

}
