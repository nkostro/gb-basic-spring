package ru.geekbrains.filter;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;

@WebFilter(urlPatterns = "/*")
public class EncodingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
        throws IOException, ServletException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");
        chain.doFilter(req, resp);
    }
}
