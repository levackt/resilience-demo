package demo.meetup.web;

import demo.meetup.client.SlowClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SlowController {

    @Autowired
    private SlowClient feign;

    @RequestMapping(path = "/slow", method = RequestMethod.GET)
    public String slow() {
        return feign.slow();
    }
}
