package ru.kpfu.itis.listeners;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import ru.kpfu.itis.repositories.*;
import ru.kpfu.itis.services.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.util.Properties;

@WebListener
public class ServletListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        Properties properties = new Properties();
        try {
            properties.load(servletContext.getResourceAsStream("WEB-INF/properties/application.properties"));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(properties.getProperty("db.driver"));
        config.setJdbcUrl(properties.getProperty("db.url"));
        config.setUsername(properties.getProperty("db.user"));
        config.setPassword(properties.getProperty("db.password"));
        config.setMaximumPoolSize(Integer.parseInt(properties.getProperty("db.hikari.pool-size")));
        HikariDataSource dataSource = new HikariDataSource(config);

        servletContext.setAttribute("dataSource", dataSource);

        AccountsRepository accountsRepository = new AccountsRepositoryJdbcTemplateImpl(dataSource);
        servletContext.setAttribute("accountsRepository", accountsRepository);

        ProjectRepository projectRepository = new ProjectRepositoryJdbcTemplateImpl(dataSource);
        servletContext.setAttribute("projectRepository", projectRepository);

        TaskRepository taskRepository = new TaskRepositoryJdbcTemplateImpl(dataSource);
        servletContext.setAttribute("taskRepository", taskRepository);

        TagRepository tagRepository = new TagRepositoryJdbcTemplateImpl(dataSource);
        servletContext.setAttribute("tagRepository", tagRepository);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        SignInService signInService = new SignInServiceJdbcImpl(accountsRepository, passwordEncoder);
        servletContext.setAttribute("signInService", signInService);

        SignUpService signUpService = new SignUpServiceJdbcImpl(accountsRepository, passwordEncoder);
        servletContext.setAttribute("signUpService", signUpService);

        ProjectService projectService = new ProjectServiceJdbcImpl(projectRepository, taskRepository, tagRepository, signInService);
        servletContext.setAttribute("projectService", projectService);

        TagService tagService = new TagServiceJdbcImpl(tagRepository, signInService);
        servletContext.setAttribute("tagService", tagService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        HikariDataSource hikariDataSource = (HikariDataSource) servletContext.getContext("dataSource");
        hikariDataSource.close();
    }
}
