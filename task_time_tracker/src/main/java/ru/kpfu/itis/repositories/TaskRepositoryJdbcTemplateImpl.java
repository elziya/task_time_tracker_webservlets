package ru.kpfu.itis.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.kpfu.itis.models.Task;

import javax.sql.DataSource;
import java.sql.PreparedStatement;

public class TaskRepositoryJdbcTemplateImpl implements TaskRepository{

    //language=SQL
    private static final String SQL_INSERT = "insert into task(name, duration)" +
            "values (?, ?)";

    private final JdbcTemplate jdbcTemplate;

    public TaskRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(Task task) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT, new String[] {"id"});

            statement.setString(1, task.getName());
            statement.setInt(2, task.getDuration());

            return statement;
        }, keyHolder);

        task.setId(keyHolder.getKey().intValue());
    }
}
