package demo.meetup.web;

import demo.meetup.client.HelloClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private HelloClient helloClient;

    @RequestMapping(path = "/hello", method = RequestMethod.GET)
    public String sayHelloToMyLittleFriends() {
        return helloClient.hello();
    }

}
