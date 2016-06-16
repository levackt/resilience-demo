package demo.meetup.web;

import demo.meetup.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.ConnectException;
import java.util.List;

@RestController
public class StoreController {

    @Autowired
    private StoreService storeService;

    @RequestMapping(path = "/stores", method = RequestMethod.GET)
    public List<String> listStores() throws ConnectException {
        return storeService.getStores();
    }
}
