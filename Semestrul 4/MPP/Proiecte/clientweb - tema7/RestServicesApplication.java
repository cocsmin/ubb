package restservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = {
        "persistance.Repo",
        "restservices"
})
@EnableJpaRepositories(basePackages = "persistance.Repo")
@EntityScan("model")
@SpringBootApplication
public class RestServicesApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestServicesApplication.class, args);
    }

}
