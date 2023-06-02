package ru.kpfu.itis.services;

import ru.kpfu.itis.models.Project;
import ru.kpfu.itis.models.Task;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ProjectService {
    void addNewTask(Task task, String projectName, boolean isDone, HttpServletRequest request);
    Project findByName(String projectName, HttpServletRequest request);
    List<Project> findAllSimple(HttpServletRequest request);
    List<Project> findAllByTag(String tagName, HttpServletRequest request);
    void save(Project project, List<String> tags,  HttpServletRequest request);
    List<Project> findAll(HttpServletRequest request);
    void deleteByName(String name, HttpServletRequest request);
    List<Project> findByPartOfName(String name, HttpServletRequest request);
}
