package demo.meetup.hystrix.camel;

import org.apache.camel.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DemoBean {

    private static final Logger LOG = LoggerFactory.getLogger(DemoBean.class);

    /**
     * Simulate some heavy lifting
     */
    public String handleRequest(@Header("fail") Boolean fail, @Header("delay") Integer delay) throws InterruptedException {
        if (fail != null && fail) {
            LOG.error("Hypnotized to fail");
            throw new IllegalStateException("Forced");
        } else if (delay != null && delay > 0) {
            Thread.sleep(delay);
            LOG.debug("Sleeping for {} millis", delay);
            return "delayed OK";
        } else {
            return "OK";
        }
    }
}
