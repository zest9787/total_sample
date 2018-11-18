package net.lucidman.samples.total_sample.web.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class BaseErrorController implements ErrorController {

    public static final String errorPath = "/error";

    @Override
    public String getErrorPath() {
        return errorPath;
    }

    @RequestMapping(value = errorPath)
    public String error(HttpServletRequest request) {

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (String.valueOf(status).equalsIgnoreCase(HttpStatus.NOT_FOUND.toString())) {
            return "thymeleaf/404";
        } else {
            return "thymeleaf/500";
        }
    }

}
