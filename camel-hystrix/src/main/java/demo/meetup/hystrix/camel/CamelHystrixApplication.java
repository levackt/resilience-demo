package demo.meetup.hystrix.camel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;


@SpringBootApplication
@EnableCircuitBreaker
@EnableHystrix
public class CamelHystrixApplication {

	public static void main(String[] args) {
		SpringApplication.run(CamelHystrixApplication.class, args);
	}

}
