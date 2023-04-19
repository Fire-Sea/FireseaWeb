package firesea.testserver.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

//    private final CorsFilter corsFilter;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)throws Exception{
        //csrf 공격 방지
        http.csrf().disable();

        //session 방식 사용하지 않음
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //cors 에러 해결 = > 다른 방식으로 해결했음
//        http.addFilter(corsFilter);

        //우선 모든 요청에 대해 허용 중
        http.authorizeRequests()
                .anyRequest().permitAll();

        return http.build();
    }
}
