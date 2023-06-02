package ru.kpfu.itis.services;

import ru.kpfu.itis.dto.SignInForm;
import ru.kpfu.itis.exceptions.NoSuchLoginException;
import ru.kpfu.itis.exceptions.WrongPasswordException;
import ru.kpfu.itis.models.Account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface SignInService {
    void signIn(SignInForm form) throws NoSuchLoginException, WrongPasswordException;
    boolean isSigned(HttpServletRequest request);
    Account getUser(HttpServletRequest request);
    void signOut(HttpServletRequest request, HttpServletResponse response);
}
