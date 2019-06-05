package wsecu;

import java.util.ArrayList;
import java.util.List;

// This is a temporary class and is being used because I can't get the H2 database 
// to work correctly.  Once that is fixed, this class can be deleted
// Note: this would also probably work with various unit testing tasks
public class UserService_Temp implements IUserService {

	// ***** TEMPORARY REPOSITORY ******
	// Populate a List to simulate the database
	private List<User> database = new ArrayList<User>();
	// ***** TEMPORARY REPOSITORY ******
	
	@Override
	public User findOne(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findOne(String userName) {
		System.out.println("user_name = " + userName);							// these should be logger methods (Log4j?)
		fillTempList();
		printTempList("Find One: initial list");
		
		for(User user : database) {
			
			System.out.println("Looking at username : " + user.getUserName());	// these should be logger methods (Log4j?)

			if(userName.equals(user.getUserName())) {
				System.out.println("User found for username : " + user.getUserName());
				return user;
			}
		}
		
		// could implement 404 error here
		System.out.println("No User found for username : " + userName);
		return null;
	}
	
	// not used
	@Override
	public List<User> findAll() {
		return database;
	}

	@Override
	public User save(User user) {
		fillTempList();
		printTempList("Save: before insert");
		
		System.out.println("Initial list size: " + database.size());

		user.setID(getNextIDValue());	// TEMPORARY - id will be max ID in the list + 1
		
		database.add(user);
		System.out.println("New list size: " + database.size());
		
		printTempList("Save: after insert");
		
		return user;
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
	}
	
	
	//////////////////////////////////////////////////
	// HELPER METHODS ////////////////////////////////
	
	// TEMPORARY method to fill the database list
	private void fillTempList() {
		if(database.size() == 0)
		{
			User user1 = new User(1, "User1", "Bob Smith", "bsmith@domain.com");
			User user2 = new User(2, "User2", "Sally Jones", "sjones@domain.com");
			User user3 = new User(3, "User3", "Roger Rabbit", "rrabbit@hoppingbunny.org");
			database.add(user1);
			database.add(user2);
			database.add(user3);		
		}	
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
	
	// find the largest ID value in the list
	// return this value + 1
	private long getNextIDValue() {
		long maxVal = 0;
		for(User user: database) {
			if(user.getId() > maxVal)
				maxVal = user.getId();
		}

		return maxVal+1;
	}

}
