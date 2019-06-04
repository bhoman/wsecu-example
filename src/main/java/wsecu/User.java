package wsecu;

public class User {
	
    private long id;
    private final String userName;
    private String name;
    private String email;

    public User(long id, String userName, String name, String email) {
        this.id = id;
        this.userName = userName;
        this.name = name;
        this.email = email;
    }

    public User(String userName, String name, String email) {
        this.userName = userName;
        this.name = name;
        this.email = email;
    }
    
    // getters
    public long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }  
    
    public String getName() {
        return name;
    }
    
    public String getEmail() {
        return email;
    }

    // setters
    // used when creating a new User or updating an existing user  
	public void setID(Long key) {
		this.id = key;		
	}

	public void setEMail(String newEmail) {
		this.email = newEmail;		
	}
	
	public void setName(String newName) {
		this.name = newName;
	}
}
