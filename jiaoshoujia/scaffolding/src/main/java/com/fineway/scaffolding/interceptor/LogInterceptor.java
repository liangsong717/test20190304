package com.fineway.scaffolding.interceptor;

import com.fineway.scaffolding.annotation.Log;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;
import java.util.Enumeration;

public class LogInterceptor extends HandlerInterceptorAdapter {
    private static final Logger log = LoggerFactory.getLogger(LogInterceptor.class);


    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            Log annotation = ((HandlerMethod) handler).getMethodAnnotation(Log.class);
            if (annotation != null) {

                String requestId = RandomStringUtils.randomAlphanumeric(8);
                request.getSession().setAttribute("beginTime", System.currentTimeMillis());
                request.getSession().setAttribute("requestId", requestId);

                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("\napi:").append(((HandlerMethod) handler).getMethod().getDeclaringClass()).append(".").append(((HandlerMethod) handler).getMethod().getName()).append("\n");
                String url = request.getRequestURL().toString() + "?" + getParameterString(request);
                String body = "body log disabled";

                if (annotation.body()) {
                    body = IOUtils.toString(request.getInputStream(), Charset.forName("UTF-8"));
                }

                Enumeration<String> headerName = request.getHeaderNames();
                stringBuilder.append("headers:");

                while (headerName.hasMoreElements()) {
                    String name = headerName.nextElement();
                    if (name.startsWith("x")) {
                        stringBuilder.append(name);
                        stringBuilder.append(":");
                        stringBuilder.append(request.getHeader(name));
                        stringBuilder.append("#");
                    }
                }
                stringBuilder.append("\nrequestId:").append(requestId);
                stringBuilder.append("\nurl:").append(request.getMethod()).append(" ").append(url);
                stringBuilder.append("\nbody:").append(body);
                log.info(stringBuilder.toString());
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            Log annotation = ((HandlerMethod) handler).getMethodAnnotation(Log.class);
            if (annotation != null) {
                String requestId = (String) request.getSession().getAttribute("requestId");
                Long beginTime = (Long) request.getSession().getAttribute("beginTime");
                if (requestId == null || beginTime == null) {
                    return;
                }

                long elapsed = System.currentTimeMillis() - beginTime;
                if (elapsed > annotation.slowRequestMillis()) {
                    log.info("slow requestId:" + requestId + ",time elapsed:" + elapsed);
                }

            }
        }
    }

    private static String getParameterString(HttpServletRequest request) {
        StringBuilder stringBuilder = new StringBuilder();
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String name = params.nextElement();
            String value = request.getParameter(name);
            stringBuilder.append(name);
            stringBuilder.append("=");
            stringBuilder.append(value);
            stringBuilder.append("&");
        }
        return stringBuilder.toString();
    }
}

