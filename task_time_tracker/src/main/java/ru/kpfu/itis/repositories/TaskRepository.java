package ru.kpfu.itis.repositories;

import ru.kpfu.itis.models.Task;

public interface TaskRepository {
    void save(Task task);
}
