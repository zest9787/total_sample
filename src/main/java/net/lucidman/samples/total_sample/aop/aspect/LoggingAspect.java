package net.lucidman.samples.total_sample.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.service.UADetectorServiceFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Around("execution(* net.lucidman.samples.total_sample.web.controller..*(..))")
    public Object executeTime(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();

        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        String apiName = className + "."+ methodName;
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String requestId = UUID.randomUUID().toString();

        log.info("******************** Request Started ********************");
        log.info("----->>>>>\nREQUESTED_ID: {}\nHOST: {} HttpMethod: {}\nURI: {}\nAPI: {}\nArguments: {}\n",
                requestId,
                request.getHeader("host"),
                request.getMethod(),
                request.getRequestURI(),
                apiName,
                Arrays.toString(joinPoint.getArgs()));

        ReadableUserAgent ua = (ReadableUserAgent) WebUtils.getSessionAttribute(request, "useragent");
        if (ua == null) {
            log.info("session useragent is null");
            UserAgentStringParser parser = UADetectorServiceFactory.getResourceModuleParser();
            String userAgent = request.getHeader("User-Agent");
            ua = parser.parse(userAgent);
            request.getSession(true).setAttribute("useragent", ua);
        }
//        UserAgent ua = WebUtils.getSessionAttribute(request, "useragent");
        if (ua != null) {
//            log.info("    UserAgent   : {}", ua);
            log.info("    Device      : {}", ua.getDeviceCategory().getCategory().getName());
            log.info("    OS          : {}", ua.getOperatingSystem().getName() + ":" + ua.getOperatingSystem().getVersionNumber());
            log.info("    Browser     : {}", ua.getFamily().getName() + ":" + ua.getVersionNumber());
        }

        if (request.getSession(false) != null) {
            log.info("    Session Id  : {}", request.getSession(false).getId());
            log.info(" creation time  : {}", new Date(request.getSession(false).getCreationTime()));
        } else {
            log.info(" Session is null ");
        }

        Object object = joinPoint.proceed();

        long elapsedTime = System.currentTimeMillis() - start;
        log.info("<<<<<-----\nExecution Time: {} ms [REQUESTED_ID: {}] [API: {}]", elapsedTime,requestId,apiName);

        return object;
    }

}
