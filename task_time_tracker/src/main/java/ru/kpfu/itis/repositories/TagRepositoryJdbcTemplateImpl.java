package ru.kpfu.itis.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import ru.kpfu.itis.models.Tag;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TagRepositoryJdbcTemplateImpl implements TagRepository{

    //language=SQL
    private static final String SQL_SELECT_BY_NAME = "select * from tag where account_id =? and tag_name = ?";

    //language=SQL
    private static final String SQL_SELECT_ALL = "select * from tag where account_id =?";

    //language=SQL
    private static final String SQL_INSERT = "insert into tag(account_id, tag_name) values (?,?)";

    private final JdbcTemplate jdbcTemplate;

    public TagRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final ResultSetExtractor<List<Tag>> simpleTagResultSetExtractor = resultSet -> {
        List<Tag> tags = new ArrayList<>();

        while (resultSet.next()) {

            Tag tag = Tag.builder()
                    .id(resultSet.getInt("id"))
                    .tagName(resultSet.getString("tag_name"))
                    .accountId(resultSet.getInt("account_id"))
                    .build();

            tags.add(tag);
        }
        return tags;
    };

    private final RowMapper<Tag> tagRowMapper = (row, rowNumber) -> Tag.builder()
        .id(row.getInt("id"))
        .tagName(row.getString("tag_name"))
        .accountId(row.getInt("account_id"))
        .build();

    @Override
    public Optional<Tag> findByName(String name, Integer accountId) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(SQL_SELECT_BY_NAME, tagRowMapper, accountId, name));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Tag> findAll(Integer accountId) {
        return jdbcTemplate.query(SQL_SELECT_ALL, simpleTagResultSetExtractor, accountId);
    }

    @Override
    public void save(Tag tag) {
        jdbcTemplate.update(SQL_INSERT, tag.getAccountId(), tag.getTagName());
    }
}
