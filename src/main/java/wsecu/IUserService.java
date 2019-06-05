package wsecu;

import java.util.List;

// Base implementation of service to manage database queries
public interface IUserService {
	
	public User findOne(long id);
	
	public User findOne(String userName);
	
	public List<User> findAll();
	
	public User save(User user);
	
	public User update(User user) throws MyResourceNotFoundException;
	
	public int delete(long id) throws MyResourceNotFoundException;

}
