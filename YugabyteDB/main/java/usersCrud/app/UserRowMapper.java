package usersCrud.app;

import org.springframework.jdbc.core.RowMapper;
import java.sql.*;

public class UserRowMapper implements RowMapper<users> {

    @Override
    public users mapRow(ResultSet rs, int rowNum) throws SQLException {
        users user = new users();
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        return user;
    }
}

