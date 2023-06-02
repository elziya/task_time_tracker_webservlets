package ru.kpfu.itis.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.kpfu.itis.models.Project;
import ru.kpfu.itis.models.Tag;
import ru.kpfu.itis.models.Task;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.*;

public class ProjectRepositoryJdbcTemplateImpl implements ProjectRepository{

    //language=SQL
    private static final String SQL_SELECT_ALL_BY_TAG = "with cte_1 as (" +
            "    select * from project_task pt left join task t on t.id = pt.task_id" +
            ")," +
            "     cte_2 as (" +
            "         select p.id as p_id, p.name as project_name, p.start_date as p_start_date, p.end_date as p_end_date," +
            "           p.duration as p_duration, p.is_done, p.account_id as p_account_id, cte_1.task_id, cte_1.name as task_name, cte_1.duration as task_duration" +
            "           from cte_1 right join project p on p.id = project_id where account_id=?" +
            ")," +
            "     cte_3 as (" +
            "         select p_id, project_name, p_start_date, p_end_date, p_duration, is_done, p_account_id, task_id, task_name, task_duration," +
            "                tag_id from cte_2 left join project_tag pt on pt.project_id = p_id" +
            "     )," +
            "     cte_4 as (" +
            "         select p_id, project_name, p_start_date, p_end_date, p_duration, is_done, p_account_id, task_id, task_name, task_duration," +
            "                tag_id, tag_name from cte_3 left join tag on tag_id = tag.id" +
            "     )" +
            "select * from cte_4 where tag_name = ?;";

    //language=SQL
    private static final String SQL_SELECT_ALL = "with cte_1 as (" +
            "    select * from project_task pt left join task t on t.id = pt.task_id" +
            ")," +
            "     cte_2 as (" +
            "         select p.id as p_id, p.name as project_name, p.start_date as p_start_date, p.end_date as p_end_date," +
            "           p.duration as p_duration, p.is_done, p.account_id as p_account_id,cte_1.task_id, cte_1.name as task_name, cte_1.duration as task_duration" +
            "           from cte_1 right join project p on p.id = project_id where account_id = ?" +
            ")," +
            "     cte_3 as (" +
            "         select p_id, project_name, p_start_date, p_end_date, p_duration, is_done, p_account_id, task_id, task_name, task_duration," +
            "                tag_id from cte_2 left join project_tag pt on pt.project_id = p_id" +
            "     )," +
            "     cte_4 as (" +
            "         select p_id, project_name, p_start_date, p_end_date, p_duration, is_done, p_account_id, task_id, task_name, task_duration," +
            "                tag_id, tag_name from cte_3 left join tag on tag_id = tag.id" +
            "     )" +
            "select * from cte_4";

    //language=SQL
    private static final String SQL_SELECT_BY_NAME = "with cte_1 as (" +
            "    select * from project_task pt left join task t on t.id = pt.task_id" +
            ")," +
            "     cte_2 as (" +
            "         select p.id as p_id, p.name as project_name, p.start_date as p_start_date, p.end_date as p_end_date," +
            "           p.duration as p_duration, p.is_done,p.account_id as p_account_id, cte_1.task_id, cte_1.name as task_name, cte_1.duration as task_duration" +
            "           from cte_1 right join project p on p.id = project_id where account_id = ? and p.name = ?" +
            ")," +
            "     cte_3 as (" +
            "         select p_id, project_name, p_start_date, p_end_date, p_duration, is_done, p_account_id, task_id, task_name, task_duration," +
            "                tag_id from cte_2 left join project_tag pt on pt.project_id = p_id" +
            "     ),\n" +
            "     cte_4 as (" +
            "         select p_id, project_name, p_start_date, p_end_date, p_duration, is_done, p_account_id, task_id, task_name, task_duration," +
            "                tag_id, tag_name from cte_3 left join tag on tag_id = tag.id" +
            "     )" +
            "select * from cte_4";

    //language=SQL
    private static final String SQL_INSERT = "insert into project(name, start_date, end_date, account_id) " +
            "values (?, ?, ?, ?);";

    //language=SQL
    private static final String SQL_INSERT_ACCOUNT_PROJECT = "insert into account_project(account_id, project_id)" +
            " values(?, ?)";

    //language=SQL
    private static final String SQL_INSERT_PROJECT_TAG = "insert into project_tag(project_id, tag_id) values(?,?)";

    //language=SQL
    private static final String SQL_UPDATE = "update project set name = ?, start_date = ?, end_date = ?, duration = ?, is_done = ? where id = ?";

    //language=SQL
    private static final String SQL_SELECT_BY_NAME_SIMPLE = "select * from project where name = ? and account_id = ?";

    //language=SQL
    private static final String SQL_SELECT_ALL_JUST_PROJECTS = "select * from project where account_id=? order by id";

    //language=SQL
    private static final String SQL_INSERT_TASK_OF_PROJECT = "insert into project_task(project_id, task_id) values(?,?)";

    //language=SQL
    private static final String SQL_DELETE = "delete from project_task where project_id = ?; delete from account_project " +
            "where project_id = ?; delete from project_tag where project_id = ?; delete from project where id = ?;";

    //language=SQL
    private static final String SQL_SELECT_BY_PART_OF_NAME = "with cte_1 as (" +
            "    select * from project_task pt left join task t on t.id = pt.task_id" +
            ")," +
            "     cte_2 as (" +
            "         select p.id as p_id, p.name as project_name, p.start_date as p_start_date, p.end_date as p_end_date," +
            "           p.duration as p_duration, p.is_done,p.account_id as p_account_id, cte_1.task_id, cte_1.name as task_name, cte_1.duration as task_duration" +
            "           from cte_1 right join project p on p.id = project_id where account_id = ? and p.name like '%'|| ? ||'%'" +
            ")," +
            "     cte_3 as (" +
            "         select p_id, project_name, p_start_date, p_end_date, p_duration, is_done, p_account_id, task_id, task_name, task_duration," +
            "                tag_id from cte_2 left join project_tag pt on pt.project_id = p_id" +
            "     ),\n" +
            "     cte_4 as (" +
            "         select p_id, project_name, p_start_date, p_end_date, p_duration, is_done, p_account_id, task_id, task_name, task_duration," +
            "                tag_id, tag_name from cte_3 left join tag on tag_id = tag.id" +
            "     )" +
            "select * from cte_4";


    private final JdbcTemplate jdbcTemplate;

    public ProjectRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final ResultSetExtractor<List<Project>> projectResultSetExtractor = resultSet -> {
        List<Project> projects = new ArrayList<>();

        Set<Integer> processedProjects = new HashSet<>();
        Project currentProject = null;

        while (resultSet.next()) {
            if (!processedProjects.contains(resultSet.getInt("p_id"))) {

                 currentProject = Project.builder()
                        .id(resultSet.getInt("p_id"))
                        .name(resultSet.getString("project_name"))
                        .startDate(resultSet.getObject("p_start_date", LocalDate.class))
                        .endDate(resultSet.getObject("p_end_date", LocalDate.class))
                        .duration(resultSet.getInt("p_duration"))
                        .accountId(resultSet.getInt("p_account_id"))
                        .isDone(resultSet.getBoolean("is_done"))
                        .tasks(new ArrayList<>())
                        .tags(new ArrayList<>())
                        .build();

                Integer tagId = resultSet.getObject("tag_id",  Integer.class);

                if (tagId != null) {
                    Tag tag = Tag.builder()
                            .id(tagId)
                            .tagName(resultSet.getString("tag_name"))
                            .accountId(resultSet.getInt("p_account_id"))
                            .build();

                    currentProject.getTags().add(tag);
                }

                projects.add(currentProject);
            }

            Integer taskId = resultSet.getObject("task_id",  Integer.class);

            if (taskId != null) {
                String name = resultSet.getString("task_name");
                Integer duration = resultSet.getInt("task_duration");

                Task task = new Task(taskId, name, duration);
                currentProject.getTasks().add(task);
            }

            processedProjects.add(currentProject.getId());
        }
        return projects;
    };

    private final ResultSetExtractor<List<Project>> simpleProjectResultSetExtractor = resultSet -> {
        List<Project> projects = new ArrayList<>();

        while (resultSet.next()) {

            Project project = Project.builder()
                    .id(resultSet.getInt("id"))
                    .name(resultSet.getString("name"))
                    .startDate(resultSet.getObject("start_date", LocalDate.class))
                    .endDate(resultSet.getObject("end_date", LocalDate.class))
                    .duration(resultSet.getInt("duration"))
                    .accountId(resultSet.getInt("account_id"))
                    .isDone(resultSet.getBoolean("is_done"))
                    .tasks(new ArrayList<>())
                    .tags(new ArrayList<>())
                    .build();

            projects.add(project);
        }
        return projects;
    };

//    private final RowMapper<Project> tagRowMapper = (row, rowNumber) -> Project.builder()
//            .id(row.getInt("id"))
//            .name(row.getString("name"))
//            .startDate(row.getObject("start_date", LocalDate.class))
//            .endDate(row.getObject("end_date", LocalDate.class))
//            .duration(row.getInt("duration"))
//            .accountId(row.getInt("account_id"))
//            .isDone(row.getBoolean("is_done"))
//            .tasks(new ArrayList<>())
//            .tags(new ArrayList<>())
//            .build();


    @Override
    public void save(Project project) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT, new String[] {"id"});

            statement.setString(1, project.getName());
            statement.setObject(2, project.getStartDate());
            statement.setObject(3, project.getEndDate());
            statement.setInt(4, project.getAccountId());

            return statement;
        }, keyHolder);

        project.setId(keyHolder.getKey().intValue());

        if (project.getTasks() != null) {
            insertTasksOfProject(project);
        }
        if (project.getTags() != null) {
            insertTagsOfProject(project);
        }

        jdbcTemplate.update(SQL_INSERT_ACCOUNT_PROJECT,project.getAccountId(), project.getId());
    }

    private void insertTagsOfProject(Project project) {
        for (Tag tag : project.getTags()) {
            jdbcTemplate.update(SQL_INSERT_PROJECT_TAG, project.getId(), tag.getId());
        }
    }

    private void insertTasksOfProject(Project project) {
        for (Task task : project.getTasks()) {
            jdbcTemplate.update(SQL_INSERT_TASK_OF_PROJECT, project.getId(), task.getId());
        }
    }

    @Override
    public void update(Project project) {
        jdbcTemplate.update(SQL_UPDATE, project.getName(), project.getStartDate(), project.getEndDate(), project.getDuration(),
                project.isDone() ,project.getId());
    }

    @Override
    public void updateProjectTasks(Project project, Task task) {
        jdbcTemplate.update(SQL_INSERT_TASK_OF_PROJECT, project.getId(), task.getId());
    }

    @Override
    public void delete(Project project) {
        Integer id = project.getId();
        jdbcTemplate.update(SQL_DELETE, id, id, id, id);
    }

    @Override
    public List<Project> findAll(Integer accountId) {
        return jdbcTemplate.query(SQL_SELECT_ALL, projectResultSetExtractor, accountId);
    }

    @Override
    public List<Project> findAllSimple(Integer accountId) {
        return jdbcTemplate.query(SQL_SELECT_ALL_JUST_PROJECTS, simpleProjectResultSetExtractor, accountId);
    }

    @Override
    public Optional<Project> findByNameSimple(String name, Integer accountId) {
        try {

            return Optional.of(jdbcTemplate.query(SQL_SELECT_BY_NAME_SIMPLE, simpleProjectResultSetExtractor, name, accountId).get(0));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Project> findByName(String name, Integer accountId) {
        try {
            return Optional.of(jdbcTemplate.query(SQL_SELECT_BY_NAME, projectResultSetExtractor,accountId, name).get(0));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Project> findByPartOfName(String name, Integer accountId) {
        return jdbcTemplate.query(SQL_SELECT_BY_PART_OF_NAME, projectResultSetExtractor, accountId, name);
    }

    @Override
    public List<Project> findAllByTag(String tag, Integer accountId) {
        return jdbcTemplate.query(SQL_SELECT_ALL_BY_TAG, projectResultSetExtractor, accountId, tag);
    }

}
