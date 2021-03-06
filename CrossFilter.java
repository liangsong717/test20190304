package com.fineway.auth.system.common.util;
 
import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
 
/**
 * Created by kay on 2017/12/7.
 */
public class CrossFilter implements Filter{
    public void init(FilterConfig filterConfig) throws ServletException {
 
    }
 
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response= (HttpServletResponse) servletResponse;
        String origin= servletRequest.getRemoteHost()+":"+servletRequest.getRemotePort();
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        //response.setHeader("Access-Control-Allow-Headers", "Authentication");
        //response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type,XFILENAME,XFILECATEGORY,XFILESIZE,x-requested-with");
        filterChain.doFilter(servletRequest,servletResponse);
    }
    public void destroy() {
 
    }
}