package ru.kpfu.itis.servlets;

import ru.kpfu.itis.models.Project;
import ru.kpfu.itis.models.Task;
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

@WebServlet("/newTasks")
public class NewTasksServlet extends HttpServlet {
    private ProjectService projectService;

    @Override
    public void init(ServletConfig config) {
        ServletContext context = config.getServletContext();
        projectService = (ProjectService) context.getAttribute("projectService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Project> projects = projectService.findAllSimple(request);
        request.setAttribute("projects", projects);
        request.getRequestDispatcher("/WEB-INF/jsp/newTasks.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Task task = Task.builder()
                .name(request.getParameter("task"))
                .duration(Integer.parseInt(request.getParameter("time")))
                .build();
        String projectName = request.getParameter("select");

        boolean isDone = request.getParameter("checked") != null;

        projectService.addNewTask(task, projectName, isDone, request);

        Project project = projectService.findByName(projectName, request);

        request.setAttribute("project", project);

        request.setAttribute("projects", projectService.findAllSimple(request));

        request.getRequestDispatcher("/WEB-INF/jsp/newTasks.jsp").forward(request, response);
    }
}
