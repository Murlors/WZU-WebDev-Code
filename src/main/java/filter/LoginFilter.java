package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "LoginFilter", urlPatterns = "/*")
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        String uri = httpServletRequest.getRequestURI();
        if (uri.contains("/js/") || uri.contains("/image/") || uri.contains("/userExistServlet") ||
                uri.contains("/register.html") || uri.contains("/registerServlet") || uri.contains("/login.html") ||
                uri.contains("/captchaServlet") || uri.contains("/loginServlet")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        HttpSession httpSession = httpServletRequest.getSession();
        String username = (String) httpSession.getAttribute("username");
        if (username == null) {
            httpServletResponse.sendRedirect("/login.html");
        } else {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }

    @Override
    public void destroy() {
    }
}
