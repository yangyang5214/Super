package com.springboot.common.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Created by zbz on 2017/2/20.
 */
@WebFilter(filterName = "securityFilter",urlPatterns = "/home2/*")
public class SecurityFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("security init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("security check");
    }

    @Override
    public void destroy() {
        System.out.println("security destory");
    }
}
