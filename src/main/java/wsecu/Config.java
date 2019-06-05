package wsecu;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan
@EnableWebMvc		// Automatically creates and registers default JSON and XML converters
public class Config {

	@Bean
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
				.addScript("schema.sql")
				.addScript("data.sql")
				.build();
	}
	
	@Bean
	public JdbcOperations jdbcTemplate(DataSource ds) {
		return new JdbcTemplate(ds);
	}
		
}
