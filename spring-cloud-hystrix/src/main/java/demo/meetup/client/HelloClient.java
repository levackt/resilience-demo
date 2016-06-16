package demo.meetup.client;

import demo.meetup.config.DebugFeignConfig;
import demo.meetup.service.HelloService;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(url = "http://${hello.host:localhost}:${hello.port:8087}", name = "hello-fallback",
        configuration = DebugFeignConfig.class, fallback = HelloService.class)
public interface HelloClient {


    @RequestMapping(value = "/hello",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    String hello();

}
