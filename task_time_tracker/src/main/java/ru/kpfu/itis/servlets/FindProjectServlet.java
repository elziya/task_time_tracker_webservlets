package ru.kpfu.itis.servlets;

import ru.kpfu.itis.models.Project;
import ru.kpfu.itis.services.ProjectService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/findProjects")
public class FindProjectServlet extends HttpServlet {
    private ProjectService projectService;

    @Override
    public void init(ServletConfig config){
        ServletContext context = config.getServletContext();
        projectService = (ProjectService) context.getAttribute("projectService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/findProjects.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if(request.getParameter("searchByTag") != null) {
            String tagName = request.getParameter("searchByTag");

            List<Project> projects = projectService.findAllByTag(tagName, request);
            request.setAttribute("projects", projects);

            if (projects.size() == 0) {
                request.setAttribute("message", "Проекты с таким тегом не найдены!");
            }
        }

        if(request.getParameter("searchByName") != null) {
            String name = request.getParameter("searchByName");

            List<Project> projects = projectService.findByPartOfName(name, request);
            request.setAttribute("projects", projects);

            if (projects.size() == 0) {
                request.setAttribute("message", "Проекты с таким именем не найдены!");
            }
        }

        request.getRequestDispatcher("/WEB-INF/jsp/findProjects.jsp").forward(request, response);
    }
}
