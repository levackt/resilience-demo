package demo.meetup.app;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;
import com.codahale.metrics.graphite.GraphiteSender;
import com.netflix.hystrix.contrib.codahalemetricspublisher.HystrixCodaHaleMetricsPublisher;
import com.netflix.hystrix.strategy.HystrixPlugins;
import com.netflix.hystrix.strategy.metrics.HystrixMetricsPublisher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableAutoConfiguration
@EnableHystrix
@ComponentScan(basePackages = {"demo.meetup.service", "demo.meetup.config", "demo.meetup.web"})
@EnableFeignClients(basePackages = "demo.meetup.client")
public class SpringFeignDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringFeignDemoApplication.class, args);

    }

    @Bean
    HystrixMetricsPublisher hystrixMetricsPublisher(MetricRegistry metricRegistry) {
        HystrixCodaHaleMetricsPublisher publisher = new HystrixCodaHaleMetricsPublisher(metricRegistry);
        HystrixPlugins.getInstance().registerMetricsPublisher(publisher);
        return publisher;
    }

    @Bean
    public GraphiteReporter graphiteReporter(MetricRegistry metricRegistry) {
        final GraphiteReporter reporter = GraphiteReporter
                .forRegistry(metricRegistry)
                .build(graphite());
        reporter.start(1, TimeUnit.SECONDS);
        return reporter;
    }

    @Bean
    GraphiteSender graphite() {
        return new Graphite(new InetSocketAddress("10.248.211.71", 8126));
    }
}
