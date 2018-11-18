package net.lucidman.samples.total_sample.listener;

import lombok.extern.slf4j.Slf4j;
import net.lucidman.samples.total_sample.event.ErrorEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Async
@Component
@Slf4j
public class ErrorEventListener implements ApplicationListener<ErrorEvent> {

    @Override
    public void onApplicationEvent(ErrorEvent errorEvent) {
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            log.error("쓰레읃 오류");
        }
        log.info("Error Event 발생");
        log.info("Evnet발생 => 오류가 발생했습니다. {}", errorEvent.getException().getMessage());
    }
}
