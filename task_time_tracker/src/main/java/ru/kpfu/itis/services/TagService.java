package ru.kpfu.itis.services;

import ru.kpfu.itis.models.Tag;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface TagService {
    List<Tag> findAll(HttpServletRequest request);
    Tag findByName(String name, HttpServletRequest request);
    void save(Tag tag, HttpServletRequest request);
}
