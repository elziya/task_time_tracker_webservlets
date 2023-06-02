package ru.kpfu.itis.servlets;

import ru.kpfu.itis.dto.SignUpForm;
import ru.kpfu.itis.exceptions.DuplicateEmailException;
import ru.kpfu.itis.exceptions.InvalidEmailException;
import ru.kpfu.itis.exceptions.WeakPasswordException;
import ru.kpfu.itis.services.SignUpService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/signup")
public class SignUpServlet extends HttpServlet {
    private SignUpService signUpService;

    @Override
    public void init(ServletConfig config) {
        ServletContext context = config.getServletContext();
        signUpService = (SignUpService) context.getAttribute("signUpService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/signUpG.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if(request.getParameter("password").equals(request.getParameter("password-repeat"))){
            SignUpForm signUpForm = SignUpForm.builder()
                    .firstName(request.getParameter("firstName"))
                    .lastName(request.getParameter("lastName"))
                    .email(request.getParameter("email"))
                    .password(request.getParameter("password"))
                    .build();
            try {
                signUpService.validateData(signUpForm);
                signUpService.signUp(signUpForm);
                response.sendRedirect(request.getContextPath() + "/signin");
                return;

            } catch (InvalidEmailException e) {
                request.setAttribute("message", "Ваш email некорректен!");
            } catch (DuplicateEmailException e) {
                request.setAttribute("message", "Пользователь с таким email уже существует!");
            } catch (WeakPasswordException e) {
                request.setAttribute("message", "Ваш пароль слишком короткий!");
            }
        }
        else {
            request.setAttribute("message", "Введенные вами пароли не совпадают");
        }
        request.getRequestDispatcher("/WEB-INF/jsp/signUpG.jsp").forward(request, response);
    }
}
