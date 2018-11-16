package net.lucidman.samples.total_sample.web.controller;

import net.lucidman.samples.total_sample.domain.User;
import net.lucidman.samples.total_sample.event.UserEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @GetMapping(value="/index")
    public String main() {
        User user = new User();
        user.setUserName("홍길동");
        UserEvent userEvent = new UserEvent(this, user);
        applicationEventPublisher.publishEvent(userEvent);

        return "index.html";
    }

}
