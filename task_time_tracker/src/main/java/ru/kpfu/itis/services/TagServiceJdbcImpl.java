package ru.kpfu.itis.services;

import ru.kpfu.itis.models.Tag;
import ru.kpfu.itis.repositories.TagRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class TagServiceJdbcImpl implements TagService{
    private final TagRepository tagRepository;
    private final SignInService signInService;


    public TagServiceJdbcImpl(TagRepository tagRepository, SignInService signInService) {
        this.tagRepository = tagRepository;
        this.signInService = signInService;
    }

    private Integer getAccountId(HttpServletRequest request) {
        return signInService.getUser(request).getId();
    }

    @Override
    public List<Tag> findAll(HttpServletRequest request) {
        return tagRepository.findAll(getAccountId(request));
    }

    @Override
    public Tag findByName(String name, HttpServletRequest request) {
        return tagRepository.findByName(name, getAccountId(request)).orElse(null);
    }

    @Override
    public void save(Tag tag, HttpServletRequest request) {
        tag.setAccountId(getAccountId(request));
        tagRepository.save(tag);
    }
}
