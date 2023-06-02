package ru.kpfu.itis.repositories;

import ru.kpfu.itis.models.Tag;

import java.util.List;
import java.util.Optional;

public interface TagRepository {
    Optional<Tag> findByName(String name, Integer accountId);
    List<Tag> findAll(Integer accountId);
    void save(Tag tag);
}
