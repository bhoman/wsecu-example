package wsecu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//import org.springframework.jdbc.core.JdbcOperations;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;

@SpringBootApplication
public class Application implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(Application.class);
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

//	@Autowired
//	JdbcOperations jdbcTemplate;
	
    @Override
    public void run(String... strings) throws Exception {

//    	AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class);
//    	UserRepository repo = ctx.getBean(UserRepository.class);
    	
        log.info("Creating tables");
        
        //repo.initializeDatabase();

//        // moved to the repository
//        jdbcTemplate.execute("DROP TABLE users IF EXISTS");
//        jdbcTemplate.execute("CREATE TABLE users(" +
//                "id SERIAL, user_name VARCHAR(255), name VARCHAR(255), email VARCHAR(255))");
//
//        // Split up the array of whole names into an array of username, name, and email
//        List<Object[]> splitUpNames = Arrays.asList(
//        		"RSMITH123/Bob Smith/bsmith@domain.com", 
//        		"SJones987/Sally Jones/sjones@domain.com", 
//        		"RRabbit555/Roger Rabbit/rrabbit@bunny.com").stream()
//                .map(name -> name.split("/"))
//                .collect(Collectors.toList());
//
//        // Use a Java 8 stream to print out each tuple of the list
//        splitUpNames.forEach(name -> log.info(String.format("Inserting user record for user_name: %s name: %s email: %s", name[0], name[1], name[2])));
//
//        // Uses JdbcTemplate's batchUpdate operation to bulk load data
//        jdbcTemplate.batchUpdate("INSERT INTO users(user_name, name, email) VALUES (?,?,?)", splitUpNames);

        log.info("Database table users created and populated...");
        //ctx.close();
        
    }
}
