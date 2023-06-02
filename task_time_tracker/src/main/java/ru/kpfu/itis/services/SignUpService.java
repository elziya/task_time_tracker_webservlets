package ru.kpfu.itis.services;

import ru.kpfu.itis.dto.SignUpForm;
import ru.kpfu.itis.exceptions.DuplicateEmailException;
import ru.kpfu.itis.exceptions.InvalidEmailException;
import ru.kpfu.itis.exceptions.WeakPasswordException;

public interface SignUpService {
    String REGEX_FOR_EMAIL = "^[a-zA-Z0-9_!#$%&amp;’*+\\\\/=?{}~^-]+(?:\\\\.[a-zA-Z0-9_!#$%&amp;’*+\\\\/=?{" +
            "}~^-]+)*@[a-zA-Z0-9-]+(?:\\\\.[a-zA-Z0-9-]+)*";

    void signUp(SignUpForm form) throws InvalidEmailException, WeakPasswordException, DuplicateEmailException;
    void validateData(SignUpForm form) throws InvalidEmailException, WeakPasswordException, DuplicateEmailException;
}
