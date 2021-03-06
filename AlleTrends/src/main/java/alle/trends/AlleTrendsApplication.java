package alle.trends;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaRepositories("repositories")
@EntityScan("pojos")
@ComponentScan(basePackages = { "views", "alle.trends", "components", "connection" })
@EnableCaching
public class AlleTrendsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlleTrendsApplication.class, args);

	}
}
