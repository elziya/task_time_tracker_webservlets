package ru.kpfu.itis.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kpfu.itis.dto.SignInForm;
import ru.kpfu.itis.exceptions.NoSuchLoginException;
import ru.kpfu.itis.exceptions.WrongPasswordException;
import ru.kpfu.itis.models.Account;
import ru.kpfu.itis.repositories.AccountsRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class SignInServiceJdbcImpl implements SignInService {

    private final AccountsRepository accountsRepository;
    private final PasswordEncoder passwordEncoder;

    public SignInServiceJdbcImpl(AccountsRepository accountsRepository, PasswordEncoder passwordEncoder) {
        this.accountsRepository = accountsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void signIn(SignInForm form) throws NoSuchLoginException, WrongPasswordException {
        String email = form.getEmail();
        String password = form.getPassword();

        Optional<Account> accountOptional = accountsRepository.findByEmail(email);

        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            if (!passwordEncoder.matches(password, account.getPasswordHash())) {
                throw new WrongPasswordException();
            }
        } else throw new NoSuchLoginException();
    }

    @Override
    public boolean isSigned(HttpServletRequest request){
        HttpSession session = request.getSession(false);

        if (session != null) {
            return session.getAttribute("email") != null;
        }
        return false;
    }

    @Override
    public Account getUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return accountsRepository.findByEmail((String) session.getAttribute("email")).orElse(null);
    }

    @Override
    public void signOut(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute("email");
    }
}
