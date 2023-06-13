package tasksCrud.app;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class JdbcTaskRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTaskRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Task createTask(int userId, Task task) {
        String sql = "INSERT INTO tasks (userid, name, description, status, tags) " +
                "VALUES (?, ?, ?, CAST (? AS STATUSENUM), ?)";
//        System.out.println
//        int n = task.getTags().size();
//        String tags[] = task.getTags().toArray(new String[task.getTags().size()]);

//        for (int i=0; i<task.getTags().size(); i++){
//            tags[i] = task.getTags().get(i);
//        }
        jdbcTemplate.update(sql, userId, task.getName(), task.getDescription(),
                task.getStatus().toString(), task.getTags().toArray(new String[task.getTags().size()]));
        return task;
    }

    public Task updateTask(int userId, Task task) {
        String sql = "UPDATE tasks SET name = ?, description = ?, status = CAST (? AS STATUSENUM), tags = ? " +
                "WHERE id = ? AND userid = ?";
        jdbcTemplate.update(
                sql,
                task.getName(),
                task.getDescription(),
                task.getStatus().toString(),
                task.getTags().toArray(new String[task.getTags().size()]),
                task.getId(),
                userId
        );

        return task;
    }

    public void deleteTask(int userId, int taskId) {
        String sql = "DELETE FROM tasks WHERE id = ? AND userid = ?";
        jdbcTemplate.update(sql, taskId, userId);
    }

    public List<Task> getAllTasksForUser(int userId) {
        String sql = "SELECT id, name, description, status, tags " +
                "FROM tasks " +
                "WHERE userid = ?";

        return jdbcTemplate.query(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            return ps;
        }, new TaskRowMapper());
    }

    public Task save(int userId, Task task) {
        if (task.getId() > 0) {
            return updateTask(userId, task);
        } else {
            return createTask(userId, task);
        }
    }


    private static class TaskRowMapper implements RowMapper<Task> {
        @Override
        public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
            Task task = new Task();
            task.setId(rs.getInt("id"));
            task.setName(rs.getString("name"));
            task.setDescription(rs.getString("description"));
            task.setStatus(StatusEnum.valueOf(rs.getString("status")));

            Array tagsArray = rs.getArray("tags");
            if (tagsArray != null) {
                Object[] tags = (Object[]) tagsArray.getArray();
                if (tags != null) {
                    List<String> tagList = new ArrayList<>();
                    for (Object tag : tags) {
                        tagList.add((String) tag);
                    }
                    task.setTags(tagList);
                }
            } else {
                task.setTags(Collections.emptyList());
            }
            return task;
        }
    }
}
