package demo.meetup.config;

import feign.Logger;
import feign.Request;
import feign.Retryer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for the typical read-only feign client. ie we'll try several times, backing off to 3 seconds etc.
 */
@Configuration
public class TestFeignConfig {

    @Value("${feign.test.maxAttempts:2}")
    private int maxAttempts;

    @Value("${feign.test.connectTimeout:100}")
    private int connectTimeout;

    @Value("${feign.test.readTimeout:500}")
    private int readTimeout;

    @Bean
    public Logger.Level feignLogger() {
        return Logger.Level.BASIC;
    }

    @Bean
    public Request.Options options() {
        return new Request.Options(connectTimeout, readTimeout);
    }

    @Bean
    public Retryer feignRetryer() {
        return new Retryer.Default(100, 100, maxAttempts);
    }

}
