package ru.kpfu.itis.services;

import ru.kpfu.itis.models.Project;
import ru.kpfu.itis.models.Tag;
import ru.kpfu.itis.models.Task;
import ru.kpfu.itis.repositories.ProjectRepository;
import ru.kpfu.itis.repositories.TagRepository;
import ru.kpfu.itis.repositories.TaskRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProjectServiceJdbcImpl implements  ProjectService{

    private final ProjectRepository projectRepository;
    private final SignInService signInService;
    private final TaskRepository taskRepository;
    private final TagRepository tagRepository;

    public ProjectServiceJdbcImpl(ProjectRepository projectRepository, TaskRepository taskRepository, TagRepository tagRepository,
                                  SignInService signInService) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.tagRepository = tagRepository;
        this.signInService = signInService;
    }

    private Integer getAccountId(HttpServletRequest request) {
        return signInService.getUser(request).getId();
    }

    @Override
    public void addNewTask(Task task, String projectName, boolean isDone, HttpServletRequest request) {
        Integer accountId = getAccountId(request);

        taskRepository.save(task);
        Project project = projectRepository.findByNameSimple(projectName, accountId).get();

        project.setDuration(project.getDuration() + task.getDuration());
        project.setEndDate(LocalDate.now());
        project.setDone(isDone);

        projectRepository.update(project);

        projectRepository.updateProjectTasks(project, task);
    }

    @Override
    public Project findByName(String projectName, HttpServletRequest request) {
        return projectRepository.findByName(projectName, getAccountId(request)).orElse(null);
    }

    @Override
    public List<Project> findAllSimple(HttpServletRequest request) {
        return projectRepository.findAllSimple(getAccountId(request));
    }

    @Override
    public List<Project> findAllByTag(String tagName, HttpServletRequest request) {
        return projectRepository.findAllByTag(tagName, getAccountId(request));
    }

    @Override
    public void save(Project project, List<String> tags, HttpServletRequest request) {
        Integer accountId = getAccountId(request);
        List<Tag> tagsOfProject = new ArrayList<>();

        for (String tag : tags) {
            tagsOfProject.add(tagRepository.findByName(tag, accountId).get());
        }

        project.setTags(tagsOfProject);
        project.setAccountId(accountId);

        projectRepository.save(project);

    }

    @Override
    public  List<Project> findAll(HttpServletRequest request) {
        return projectRepository.findAll(getAccountId(request));
    }

    @Override
    public void deleteByName(String name, HttpServletRequest request) {
        projectRepository.delete(projectRepository.findByName(name, getAccountId(request)).get());
    }

    @Override
    public List<Project> findByPartOfName(String name, HttpServletRequest request) {
        return projectRepository.findByPartOfName(name, getAccountId(request));
    }
}
