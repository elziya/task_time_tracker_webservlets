package ru.kpfu.itis.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kpfu.itis.dto.SignUpForm;
import ru.kpfu.itis.exceptions.DuplicateEmailException;
import ru.kpfu.itis.exceptions.InvalidEmailException;
import ru.kpfu.itis.exceptions.WeakPasswordException;
import ru.kpfu.itis.models.Account;
import ru.kpfu.itis.repositories.AccountsRepository;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpServiceJdbcImpl implements SignUpService {

    private final AccountsRepository accountsRepository;
    private final PasswordEncoder passwordEncoder;

    public SignUpServiceJdbcImpl(AccountsRepository accountsRepository, PasswordEncoder passwordEncoder) {
        this.accountsRepository = accountsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void signUp(SignUpForm form) {
        String passwordHash = passwordEncoder.encode(form.getPassword());

        Account account = Account.builder()
                .firstName(form.getFirstName())
                .lastName(form.getLastName())
                .email(form.getEmail())
                .passwordHash(passwordHash)
                .build();


        accountsRepository.save(account);
    }

    @Override
    public void validateData(SignUpForm form) throws InvalidEmailException, WeakPasswordException, DuplicateEmailException {
        Pattern pattern = Pattern.compile(REGEX_FOR_EMAIL);
        Matcher matcher = pattern.matcher(form.getEmail());

        if (!matcher.find()) {
            throw new InvalidEmailException();
        }

        if (accountsRepository.findByEmail(form.getEmail()).isPresent()) {
            throw new DuplicateEmailException();
        }

        if (form.getPassword().length() < 8) {
            throw new WeakPasswordException();
        }
    }
}
