package com.fineway.scaffolding.filter;


import com.fineway.scaffolding.wrapper.HttpServletRequestInputStreamWrapper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/api/*")
public class HttpServletRequestInputStreamFilter implements Filter {

    private final int MAX_REQUEST_LENGTH = 512 * 1024;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //避免内存压力过大,大于512k的请求不使用wrapper
        if (request.getContentLength() > MAX_REQUEST_LENGTH) {
            chain.doFilter(request, response);
        }
//        if (request.getContentType().startsWith("multipart/")){
//            chain.doFilter(request, response);
//        }
        chain.doFilter(new HttpServletRequestInputStreamWrapper((HttpServletRequest) request), response);
    }

    @Override
    public void destroy() {

    }
}
