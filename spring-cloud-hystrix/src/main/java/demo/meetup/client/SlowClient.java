package demo.meetup.client;

import demo.meetup.config.TestFeignConfig;
import demo.meetup.service.FallbackSlowService;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(url = "http://${hello.host:localhost}:${hello.port:8087}", name = "slow", fallback = FallbackSlowService.class,
        configuration = TestFeignConfig.class)
public interface SlowClient {

    @RequestMapping(method = RequestMethod.GET, value = "/slow",
        produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String slow();
}
