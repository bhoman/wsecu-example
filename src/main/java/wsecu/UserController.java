package wsecu;

//import java.util.List;
//import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/user")
class UserController {

	// atomic ID value - unique even with multiple sessions running
	//private final AtomicLong counter = new AtomicLong();	
	
//	@GetMapping
//	public List<User> getAll() {
//		UserRepository repo = new UserRepositoryImpl();
//		
//		return repo.findAll();
//	}
	
	
	@GetMapping
	@ResponseBody
	private User getUser(@RequestParam(value="username", required=true) String userName) {
		// read from the database
		UserRepository repo = new UserRepositoryImpl();
		
		return repo.findOne(userName);
		
		// handle error message if username not supplied
		// handle error message if database does not find username
		// handle blank username
		//return new User(1000, userName, "Fake name", "Fake email");
	}
	
	// add a new user with default values if not specified
	@PostMapping
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	private User postUser(
			@RequestParam(value="username", defaultValue="User Name Not Specified") String userName,
			@RequestParam(value="name", defaultValue="Name Not Specified") String name,
			@RequestParam(value="email", defaultValue="Email Not Specified") String email) {
		
		// write to the database here with try/catch
		UserRepository repo = new UserRepositoryImpl();
		User user = new User(userName, name, email);	// id will be defined by the database
		repo.save(user);
		return user;
	}
	
	// delete a user based on the user's ID
	@DeleteMapping
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	private void deleteUser(
			@RequestParam(value="id", required=true) long id) {

		try {
			UserRepository repo = new UserRepositoryImpl();
			repo.delete(id);
		}
		catch(MyResourceNotFoundException exc)
		{
			// Implement 404 error
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "User not found.  No records were deleted.");
		}
	}
	
	// update a user's email and name based on the user's ID
	@PutMapping
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	private User putUser(
			@RequestParam(value="id", required=true) long id,
			@RequestParam(value="name", defaultValue="Name Not Specified") String name,
			@RequestParam(value="email", defaultValue="Email Not Specified") String email) {
		

		// write to the database here, retrieve the userName with try/catch
		UserRepository repo = new UserRepositoryImpl();
		User user = new User(id, "", name, email);
		
		try {
			User result = repo.update(user);
			return result;
		}
		catch(MyResourceNotFoundException exc)
		{
			// Implement 404 error
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "User not found.  No records were updated.");
		}
	}
}
