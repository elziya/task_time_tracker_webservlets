package ru.kpfu.itis.servlets;

import ru.kpfu.itis.models.Project;
import ru.kpfu.itis.models.Tag;
import ru.kpfu.itis.services.ProjectService;
import ru.kpfu.itis.services.TagService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/projects")
public class WorkWithProjectsServlet extends HttpServlet {
    private ProjectService projectService;
    private TagService tagService;

    @Override
    public void init(ServletConfig config){
        ServletContext context = config.getServletContext();
        projectService = (ProjectService) context.getAttribute("projectService");
        tagService = (TagService) context.getAttribute("tagService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setAttributes(request);
        request.getRequestDispatcher("/WEB-INF/jsp/workWithProjects.jsp").forward(request, response);
    }

    private void setAttributes(HttpServletRequest request){
        List<Project> projectsSimple = projectService.findAllSimple(request);
        request.setAttribute("projectsSimple", projectsSimple);

        List<Tag> tags = tagService.findAll(request);
        request.setAttribute("tags", tags);

        List<Project> projects = projectService.findAll(request);
        request.setAttribute("projects", projects);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String projectName = request.getParameter("projectName");

        if (projectName != null) {
            Project project = Project.builder()
                    .name(request.getParameter("projectName"))
                    .startDate(LocalDate.now())
                    .endDate(LocalDate.now())
                    .isDone(false)
                    .build();

            List<String> tags = new ArrayList<>();
            tags.add(request.getParameter("selectTag"));

            projectService.save(project, tags, request);
        }

        if (request.getParameter("selectProject") != null) {
            String projectToDelete = request.getParameter("selectProject");
            projectService.deleteByName(projectToDelete, request);
        }
        setAttributes(request);

        request.getRequestDispatcher("/WEB-INF/jsp/workWithProjects.jsp").forward(request, response);
    }
}
