package io.rhiot.quickstarts.spark;

import io.rhiot.cloudplatform.runtime.spring.CloudPlatform;
import org.apache.camel.component.spark.annotations.RddCallback;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

import static java.lang.Integer.parseInt;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.apache.camel.component.spark.annotations.AnnotatedRddCallback.annotatedRddCallback;
import static org.slf4j.LoggerFactory.getLogger;

@Configuration
public class RhiotJob {

    private static final Logger LOG = getLogger(RhiotJob.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        new CloudPlatform().start(args);
        while (true) {
            LOG.info("This is heartbeat from Rhiot Spark Job.");
            SECONDS.sleep(5);
        }
    }

    // Job details

    @Bean
    JavaRDD rdd(JavaSparkContext sparkContext) {
        return sparkContext.textFile("/etc/fstab").cache();
    }

    @Bean
    Object countAndMultiply() {
        return annotatedRddCallback(new Object(){
            @RddCallback
            long countAndMultiply(JavaRDD rdd, String n) {
                return rdd.count() * parseInt(n);
            }
        });
    }

}