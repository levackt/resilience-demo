package demo.meetup.service;

import demo.meetup.client.SlowClient;
import org.springframework.stereotype.Component;

@Component
public class FallbackSlowService implements SlowClient {

    @Override
    public String slow() {
        return "You may want to try a premium service instead";
    }
}