package ru.kpfu.itis.repositories;

import ru.kpfu.itis.models.Project;
import ru.kpfu.itis.models.Task;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository {

    void save(Project project);
    void update(Project project);
    void updateProjectTasks(Project project, Task task);
    void delete(Project project);
    List<Project> findAll(Integer accountId);
    List<Project> findAllSimple(Integer accountId);
    Optional<Project> findByNameSimple(String name, Integer accountId);
    List<Project> findAllByTag(String tag, Integer accountId);
    Optional<Project> findByName(String name, Integer accountId);
    List<Project> findByPartOfName(String name, Integer accountId);
}
