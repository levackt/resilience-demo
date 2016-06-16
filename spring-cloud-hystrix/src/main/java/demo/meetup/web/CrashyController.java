package demo.meetup.web;

import demo.meetup.commands.CrashyCommand;
import demo.meetup.service.ErrorProneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicBoolean;

@RestController
public class CrashyController {

    @Autowired
    private ErrorProneService service;

    @RequestMapping(path = "/crash", method = RequestMethod.GET)
    public String crash() {
        return service.maybeDoSomethingCool();
    }
}
