package wsecu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;


@Service
public class UserService implements IUserService {

	@Autowired
	private JdbcOperations jdbc;
	
	// constants for database query strings
	private static final String SQL_INSERT = "insert into users (username, name, email values (?,?,?)";
	private static final String SQL_UPDATE = "update users set username =?, name=?, email=? where id=?";
	private static final String SQL_FIND_ONE_BY_USER_NAME = "select * from users where username=?)";
	private static final String SQL_FIND_ONE_BY_ID = "select * from users where id=?)";
	private static final String SQL_FIND_ALL = "select * from users";
	private static final String SQL_DELETE_ONE = "delete from users where id=?)";
	private static final String SQL_GET_COUNT = "select coalesce(count(*), -1) from users where username != ?";
	
	@Override
	public User findOne(long id) {
		return jdbc.queryForObject(SQL_FIND_ONE_BY_ID, new UserRowMapper(), id);
	}
	
	
	@Override
	public User findOne(String userName) {
		// ******************************
		// this should work but does not.  The QueryForObject is throwing a NullPointerException for some reason
		//commented out until I can figure this out
		
		// debug statements
		System.out.println("userName = " + userName);
		int cnt = jdbc.queryForObject(SQL_GET_COUNT, new Object[] { userName }, Integer.class);	// does the table contain data?
		System.out.println("count = " + cnt);
		// end debug
		
		Long id = jdbc.queryForObject(SQL_FIND_ONE_BY_USER_NAME, new Object[] { "username" }, Long.class);
		if(id == null) {
			// user name was not found
			return null;	// throw Exception of some type?
		}
		else
			return findOne(id);
		 //******************************
	}
	

	@Override
	public List<User> findAll() {
		return jdbc.query(SQL_FIND_ALL, new UserRowMapper());
	}

	// TODO - add code to prevent duplicate user names
	@Override
	public User save(User user) {
		// ******************************
		// this should work but does not.  The QueryForObject is throwing a NullPointerException for some reason
		// commented out until I can figure this out
		
		KeyHolder holder = new GeneratedKeyHolder();
		
		int rows = jdbc.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(SQL_INSERT, new String[] {"id"});
				
				ps.setString(1,  user.getUserName());
				ps.setString(2, user.getName());
				ps.setString(3, user.getEmail());
				
				return ps;
			}
		}, holder);
		
		if(rows == 1) {
			user.setID((Long)holder.getKey());
			return user;
		}
		
		// something failed
		return null;
		// ******************************
	}

	@Override
	public User update(User user) {
		// ******************************
		// this should work but does not.  The QueryForObject is throwing a NullPointerException for some reason
		// commented out until I can figure this out
		int rows = jdbc.update(SQL_UPDATE, user.getUserName(), user.getName(), user.getEmail());
		if(rows == 1)
			return user;
		else
			return null;
		// ******************************
	}

	@Override
	public int delete(long id) {
		
		// ******************************
		// this should work but does not.  The QueryForObject is throwing a NullPointerException for some reason
		// commented out until I can figure this out
		return jdbc.update(SQL_DELETE_ONE, id);
		// ******************************

	}
	
	///////////////////////////////////////////////////////////////////////////
	// row mapper implementation - convert dataset rows to a User object
	private class UserRowMapper implements RowMapper<User> {

		@Override
		public User mapRow(ResultSet rs, int row) throws SQLException {
			return new User(rs.getLong("id"), rs.getString("username"), rs.getString("name"), rs.getString("email"));
		}
	}
}
