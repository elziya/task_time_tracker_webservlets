package ru.kpfu.itis.servlets;

import ru.kpfu.itis.models.Tag;
import ru.kpfu.itis.services.TagService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/tags")
public class WorkWithTagsServlet extends HttpServlet{
    private TagService tagService;

    @Override
    public void init(ServletConfig config){
        ServletContext context = config.getServletContext();
        tagService = (TagService) context.getAttribute("tagService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setAttribute(request);
        request.getRequestDispatcher("/WEB-INF/jsp/workWithTags.jsp").forward(request, response);
    }

    private void setAttribute(HttpServletRequest request){
        List<Tag> tags = tagService.findAll(request);
        request.setAttribute("tags", tags);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if(tagService.findByName(request.getParameter("tagName"), request) != null){
            request.setAttribute("message", "Такой тег уже существует!");
        }
        else {
            Tag tag = new Tag();
            tag.setTagName(request.getParameter("tagName"));
            tagService.save(tag, request);
        }
        setAttribute(request);

        request.getRequestDispatcher("/WEB-INF/jsp/workWithTags.jsp").forward(request, response);
    }

}
