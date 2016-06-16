package demo.meetup.service;

import demo.meetup.client.HelloClient;
import org.springframework.stereotype.Component;

@Component
public class HelloService implements HelloClient {

    @Override
    public String hello() {
        return "Hello, from local fallback";
    }
}