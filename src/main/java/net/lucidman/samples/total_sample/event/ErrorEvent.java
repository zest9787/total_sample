package net.lucidman.samples.total_sample.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ErrorEvent extends ApplicationEvent {

    private Exception exception;

    public ErrorEvent(Object source, Exception e) {
        super(source);
        this.exception = e;
    }

}
