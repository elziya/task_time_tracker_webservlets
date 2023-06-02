package ru.kpfu.itis.servlets;

import ru.kpfu.itis.dto.SignInForm;
import ru.kpfu.itis.exceptions.NoSuchLoginException;
import ru.kpfu.itis.exceptions.WrongPasswordException;
import ru.kpfu.itis.services.SignInService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/signin")
public class SignInServlet extends HttpServlet {
    private SignInService signInService;

    @Override
    public void init(ServletConfig config) {
        ServletContext context = config.getServletContext();
        signInService = (SignInService) context.getAttribute("signInService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/signInG.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        SignInForm form = SignInForm.builder()
                .email(request.getParameter("email"))
                .password(request.getParameter("password"))
                .build();

        if (form.getEmail() != null && form.getPassword() != null){

            request.setAttribute("login", request.getParameter("email"));
            try {
                signInService.signIn(form);
                HttpSession session = request.getSession(true);
                session.setAttribute("email", form.getEmail());
                response.sendRedirect(request.getContextPath() + "/newTasks");
                return;

            } catch (NoSuchLoginException e) {
                request.setAttribute("message", "Такого логина не существует!");
            } catch (WrongPasswordException e) {
                request.setAttribute("message", "Вы ввели неправильный пароль!");
            }
        }

        request.getRequestDispatcher("/WEB-INF/jsp/signInG.jsp").forward(request, response);
    }

}
