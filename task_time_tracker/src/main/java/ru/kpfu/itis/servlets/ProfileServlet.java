package ru.kpfu.itis.servlets;

import ru.kpfu.itis.services.SignInService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    private ServletContext context;
    private SignInService signInService;

    @Override
    public void init(ServletConfig config) {
        context = config.getServletContext();
        signInService = (SignInService) context.getAttribute("signInService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("account", signInService.getUser(request));

        String action = request.getParameter("action");

        if (action != null) {
            signInService.signOut(request, response);
            response.sendRedirect(context.getContextPath() + "/signin");
            return;
        }

        request.getRequestDispatcher("/WEB-INF/jsp/profileG.jsp").forward(request, response);
    }
}
