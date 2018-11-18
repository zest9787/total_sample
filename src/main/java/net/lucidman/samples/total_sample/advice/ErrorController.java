package net.lucidman.samples.total_sample.advice;

import lombok.extern.slf4j.Slf4j;
import net.lucidman.samples.total_sample.domain.ErrorMessage;
import net.lucidman.samples.total_sample.event.ErrorEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@ControllerAdvice
@Slf4j
public class ErrorController {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    protected ErrorMessage errorMessage;

    @ExceptionHandler(Exception.class)
    public String handleAnyException(HttpServletRequest request, HttpServletResponse response, Exception e) {
//        ExceptionDto exception = new ExceptionDto("Exception on server occurred", e.toString(), ExceptionType.SERVER);
        request.setAttribute("msg", "serverexception");
        log.info("error server exception");
        log.info("에러 이벤트 publish");
        applicationEventPublisher.publishEvent(new ErrorEvent(this, e));
        log.info("에러 이벤트 publish완료");
        return "forward:/handling";
    }

    @ExceptionHandler(SQLException.class)
    public String sqlException(HttpServletRequest request, Exception e) {
//        ExceptionDto exception = new ExceptionDto("SQLException occurred", e.getCause().getMessage(), ExceptionType.SQL);
        request.setAttribute("msg", "aaaaaa : " + e.getCause().getMessage());
        log.info("error : " + e.getCause().getMessage());
        log.info("에러 이벤트 publish");
        applicationEventPublisher.publishEvent(new ErrorEvent(this, e));
        log.info("에러 이벤트 publish완료");
        return "forward:/handling";
    }

}
