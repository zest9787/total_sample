package net.lucidman.samples.total_sample.web.controller;

import lombok.extern.slf4j.Slf4j;
import net.lucidman.samples.total_sample.domain.User;
import net.lucidman.samples.total_sample.event.UserEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@Slf4j
public class MainController {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @GetMapping(value="/index")
    public String main() {
        User user = new User();
        user.setUserName("홍길동");
        UserEvent userEvent = new UserEvent(this, user);
        applicationEventPublisher.publishEvent(userEvent);

        return "thymeleaf/index.html";
    }


    @RequestMapping("/servlet_error")
    public String servlet_error() {
        log.info("servlet_error request");
        return "servlet_error";
    }

//    @RequestMapping("/")
//    public String exceptionTest() {
//        return "thymeleaf/exceptionTest";
//    }
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView showHome(@RequestParam(value = "viewResolver") Optional<String> viewResolver) {
        return getDefaultView(viewResolver);
    }

    private ModelAndView getDefaultView(Optional<String> viewResolver) {
        ModelAndView model = new ModelAndView(createView(viewResolver, "/exceptionTest"));
        model.addObject("name", "Spring Squad");
        return model;
    }

    private String createView(Optional<String> viewResolver, String viewName) {
        String result = viewResolver.isPresent() ? viewResolver.get() : "jsp";
        result += viewName;
        return result;
    }

    @RequestMapping("/403")
    public String forBiddenPage() {
        return "thymeleaf/403";
    }

    @RequestMapping("/404")
    public String notFoundPage() {
        return "thymeleaf/404";
    }

    @RequestMapping("/405")
    public String internalServerErrorPage() {
        return "thymeleaf/405";
    }

    @RequestMapping(value = "/handling")
    public ModelAndView sampleTest(HttpServletRequest request) {
        log.info("handling page");
        String msg = (String) request.getAttribute("msg");
        ModelAndView mv = new ModelAndView("thymeleaf/500");
        mv.addObject("msg", msg);
        return mv;
    }

    @RequestMapping(value = "/handling", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public ResponseEntity<String> handleCustomException(HttpServletRequest request) {
        log.info("handling json");
        String msg =  (String) request.getAttribute("msg");
        return new ResponseEntity<String>(msg, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @RequestMapping(value="/exceptionOnHtmlRequst")
    public String exceptionOnHtmlRequest() throws NullPointerException {
        log.info("exceptionOnHtmlRequst");
        throw new NullPointerException("null point오류");
    }

    @GetMapping("/sqlExceptionOnHtmlRequest")
    public String sqlExceptionOnHtmlRequest() throws SQLException {
        log.info("sqlExceptionOnHtmlRequest");
        throw new SQLException("SQLExceptin", new SQLException("SQL_101"));
    }

    @GetMapping("/exceptionOnJsonRequest")
    @ResponseBody
    public List<Map<String, Object>> exceptionOnJsonRequest() throws Exception{
        throw new Exception();
    }

    @GetMapping("/sqlExceptionOnJsonRequest")
    @ResponseBody
    public List<Map<String, Object>> sqlExceptionOnJsonRequest() throws SQLException {
        throw new SQLException("SQLExceptin", new SQLException("SQL_101"));
    }
}
