package demo.meetup.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import demo.meetup.commands.CrashyCommand;
import org.springframework.stereotype.Component;

@Component
public class ErrorProneService {

    @HystrixCommand(fallbackMethod = "fallback")
    public String maybeDoSomethingCool() {
        return new CrashyCommand().execute();
    }

    private String fallback() {
        return "Got your back";
    }
}
