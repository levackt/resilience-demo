package demo.meetup.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Component;

import java.net.ConnectException;
import java.util.Arrays;
import java.util.List;

@Component
public class StoreService {

    @HystrixCommand(fallbackMethod = "defaultStores")
    public List<String> getStores() throws ConnectException {
        throw new ConnectException("Forced");
    }

    public List<String> defaultStores() {
        return Arrays.asList("Acme");
    }
}
