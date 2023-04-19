package firesea.testserver;

import firesea.testserver.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.beans.BeanProperty;
import java.util.Optional;

@SpringBootApplication
@EnableJpaAuditing
@RequiredArgsConstructor
public class TestServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestServerApplication.class, args);
	}

	@Bean
	public AuditorAware<String> auditorProvider() {
		return ()-> Optional.of("익명");
	}

}
