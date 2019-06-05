package wsecu;

import java.util.List;
import org.springframework.stereotype.Repository;


// Concrete implementation class for the UserReposity interface
@Repository
public class UserRepository implements IUserRepository {

	private IUserService service;

	// Dependency Injection via the constructor
	public UserRepository(IUserService service) {
		this.service = service;
	}
	
	
	@Override
	public User findOne(long id) {

		return service.findOne(id);
	}
	
	@Override
	public User findOne(String userName) {

		return service.findOne(userName);
	}


	@Override
	public User save(final User user) {

		return service.save(user);
	}

	// not used, but also fails on NullPointerException
	@Override
	public List<User> findAll() {
		return service.findAll();
	}

	@Override
	public User update(User user) throws MyResourceNotFoundException {	
				
		return service.update(user);
	}

	@Override
	public int delete(long id) throws MyResourceNotFoundException {

		return service.delete(id);

	}

	
	

}
