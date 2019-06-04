package wsecu;

//import java.sql.Connection;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
//import java.util.Arrays;
import java.util.List;
//import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
//import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
//import org.springframework.jdbc.support.GeneratedKeyHolder;
//import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

// Concrete implementation class for the UserReposity interface
@Repository
public class UserRepositoryImpl implements UserRepository {

	@Autowired
	private JdbcOperations jdbc;
	
	// ***** TEMPORARY REPOSITORY ******
	// Populate a List to simulate the database
	private List<User> database = new ArrayList<User>();
	// ***** TEMPORARY REPOSITORY ******
	
	
	// constants for database query strings
	//private static final String SQL_INSERT = "insert into users (username, name, email values (?,?,?)";
	//private static final String SQL_UPDATE = "update users set username =?, name=?, email=? where id=?";
	//private static final String SQL_FIND_ONE_BY_USER_NAME = "select * from users where username=?)";
	private static final String SQL_FIND_ONE_BY_ID = "select * from users where id=?)";
	private static final String SQL_FIND_ALL = "select * from users";
	//private static final String SQL_DELETE_ONE = "delete from users where id=?)";
	//private static final String SQL_GET_COUNT = "select coalesce(count(*), -1) from users where username != ?";
	
	@Override
	public User findOne(long id) {

		return jdbc.queryForObject(SQL_FIND_ONE_BY_ID, new UserRowMapper(), id);
	}
	
	@Override
	public User findOne(String user_name) {
		System.out.println("user_name = " + user_name);							// these should be logger methods (Log4j?)
		fillTempList();
		printTempList("Find One: initial list");
		
		for(User user : database) {
			
			System.out.println("Looking at username : " + user.getUserName());	// these should be logger methods (Log4j?)

			if(user_name.equals(user.getUserName())) {
				System.out.println("User found for username : " + user.getUserName());
				return user;
			}
		}
		
		// could implement 404 error here
		System.out.println("No User found for username : " + user_name);
		return null;

		// ******************************
		// this should work but does not.  The QueryForObject is throwing a NullPointerException for some reason
		// commented out until I can figure this out
		
		//System.out.println("user_name = " + user_name);
		//int cnt = jdbc.queryForObject(SQL_GET_COUNT, new Object[] { user_name }, Integer.class);	// does the table contain data?
		//System.out.println("count = " + cnt);
		
//		Long id = jdbc.queryForObject(SQL_FIND_ONE_BY_USER_NAME, new Object[] { "username" }, Long.class);
//		if(id == null) {
//			// user name was not found
//			return null;	// throw Exception of some type?
//		}
//		else
//			return findOne(id);
		// ******************************

	}

	// TEMPORARY method to fill the database list
	private void fillTempList() {
		User user1 = new User(1, "User1", "Bob Smith", "bsmith@domain.com");
		User user2 = new User(2, "User2", "Sally Jones", "sjones@domain.com");
		User user3 = new User(3, "User3", "Roger Rabbit", "rrabbit@hoppingbunny.org");
		database.add(user1);
		database.add(user2);
		database.add(user3);	
	}
	
	// these should be logger methods (Log4j?)
	private void printTempList(String action) {
		System.out.println(action);
		for(User user: database) {
			System.out.println( "Id: " + user.getId() + 
								" User name: " + user.getUserName() +
								" Name: " + user.getName() +
								" EMail: " + user.getEmail());
		}
			
	}

	// TODO - add code to prevent duplicate user names
	@Override
	public User save(final User user) {

		// TEMPORARY - id will be list length + 1
		fillTempList();
		printTempList("Save: before insert");
		
		System.out.println("Initial list size: " + database.size());
		int len = database.size();
		len = len+1;
		long llen = len;
		user.setID(llen);
		database.add(user);
		System.out.println("New list size: " + database.size());
		
		printTempList("Save: after insert");
		
		return database.get((int) user.getId()-1);
		
		// ******************************
		// this should work but does not.  The QueryForObject is throwing a NullPointerException for some reason
		// commented out until I can figure this out
		
//		KeyHolder holder = new GeneratedKeyHolder();
//		
//		int rows = jdbc.update(new PreparedStatementCreator() {
//
//			@Override
//			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
//				PreparedStatement ps = conn.prepareStatement(SQL_INSERT, new String[] {"id"});
//				
//				ps.setString(1,  user.getUserName());
//				ps.setString(2, user.getName());
//				ps.setString(3, user.getEmail());
//				
//				return ps;
//			}
//		}, holder);
//		
//		if(rows == 1) {
//			user.setID((Long)holder.getKey());
//			return user;
//		}
//		
//		// something failed
//		return null;
		// ******************************
	}

	// not used, but also fails on NullPointerException
	@Override
	public List<User> findAll() {
		return jdbc.query(SQL_FIND_ALL, new UserRowMapper());
	}

	@Override
	public User update(User user) throws MyResourceNotFoundException {
		fillTempList();
		printTempList("Update: before update");
		
		User userToUpdate = null;
		try
		{
			userToUpdate = database.get((int) user.getId()-1);
		}
		catch (IndexOutOfBoundsException e)
		{
			throw new MyResourceNotFoundException();
		}

		if(userToUpdate != null) {
			userToUpdate.setEMail(user.getEmail());
			userToUpdate.setName(user.getName());
		
			printTempList("Update: after update");
			return userToUpdate;
		}
		else
			throw new MyResourceNotFoundException();	// no record found to update
		
				
		// ******************************
		// this should work but does not.  The QueryForObject is throwing a NullPointerException for some reason
		// commented out until I can figure this out
		//return jdbc.update(SQL_UPDATE, user.getUserName(), user.getName(), user.getEmail());
		// ******************************
	}

	@Override
	public int delete(long id) throws MyResourceNotFoundException {
		fillTempList();
		printTempList("Deleted: before delete");

		User userToUpdate = null;
		try
		{
			userToUpdate = database.get((int) id-1);
		}
		catch (IndexOutOfBoundsException e)
		{
			throw new MyResourceNotFoundException();
		}

		if(userToUpdate != null) {
			database.remove((int)id-1);
			
			printTempList("Delete: after delete");
			return 1;	// in real code, this would be number of records deleted
		}
		else
			throw new MyResourceNotFoundException();	// no record found to update
		
		
		// ******************************
		// this should work but does not.  The QueryForObject is throwing a NullPointerException for some reason
		// commented out until I can figure this out
		//return jdbc.update(SQL_DELETE_ONE, user.getId());
		// ******************************
	}

	
	
	// row mapper implementation - convert dataset rows to a User object
	private class UserRowMapper implements RowMapper<User> {

		@Override
		public User mapRow(ResultSet rs, int row) throws SQLException {
			return new User(rs.getLong("id"), rs.getString("username"), rs.getString("name"), rs.getString("email"));
		}
	}
}
