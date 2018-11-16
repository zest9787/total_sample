package net.lucidman.samples.total_sample.listener;

import lombok.extern.slf4j.Slf4j;
import net.lucidman.samples.total_sample.event.UserEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserEventListner implements ApplicationListener<UserEvent> {

    @Override
    public void onApplicationEvent(UserEvent userEvent) {
        log.info("UserEventListener : " + userEvent.getUser().getUserName());
    }
}
