package wsecu;

import java.util.List;

// Interface to define the methods to access the Users table
public interface UserRepository {
	
	User findOne(long id);	// by ID
	
	User findOne(String user_name);	// by User name	
	
	User save(User user);
	
	List<User> findAll();
	
	User update(User user);
	
	int delete(long id);
	
}
