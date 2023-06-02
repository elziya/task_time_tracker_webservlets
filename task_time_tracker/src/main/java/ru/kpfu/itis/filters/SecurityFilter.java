package ru.kpfu.itis.filters;

import ru.kpfu.itis.services.SignInService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class SecurityFilter implements Filter {

    private String[] protectedPaths;
    private SignInService signInService;

    @Override
    public void init(FilterConfig filterConfig) {
        protectedPaths = new String[]{"/profile", "/newTasks", "/findProjects", "/projects", "/tags"};
        ServletContext servletContext = filterConfig.getServletContext();
        this.signInService = (SignInService) servletContext.getAttribute("signInService");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        boolean prot= false;

        for(String path : protectedPaths){
            if(path.equals(request.getRequestURI().substring(request.getContextPath().length()))){
                prot = true;
                break;
            }
        }

        if(prot && !signInService.isSigned(request)){
            response.sendRedirect(request.getContextPath() + "/signin");
        }

        filterChain.doFilter(request,response);

    }
}
