package net.lucidman.samples.total_sample.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorMessage {
    private String status;
    private String code;
    private String msg;
}
