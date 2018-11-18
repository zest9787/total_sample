package net.lucidman.samples.total_sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
@EnableAspectJAutoProxy
public class TotalSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(TotalSampleApplication.class, args);
    }
}
