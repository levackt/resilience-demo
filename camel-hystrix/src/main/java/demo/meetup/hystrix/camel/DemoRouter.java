package demo.meetup.hystrix.camel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class DemoRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("undertow:http://0.0.0.0:8081/demo")
                .hystrix()
                .hystrixConfiguration()
                        .queueSizeRejectionThreshold(2)
                        .circuitBreakerErrorThresholdPercentage(10)
                   .executionTimeoutInMilliseconds(250) //caller will timeout and walk away from the command execution
//                    .executionIsolationStrategy("THREAD") // separate thread and concurrent requests are limited by the number of threads in the thread-pool
//                    .executionIsolationStrategy("SEMAPHORE") //executes on the calling thread and concurrent requests are limited by the semaphore count
                .end()
                .bean(DemoBean.class)
//                .onFallbackViaNetwork() //on it's own thread
                .onFallback() //ideally local/reliable fallback
                .log("Falling back")
                .transform(constant("Still OK"))
                .end();
    }
}
