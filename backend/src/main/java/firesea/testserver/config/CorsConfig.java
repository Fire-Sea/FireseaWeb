package firesea.testserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

//@Configuration
public class CorsConfig {
    // 인증이 필요하지 않은 요청만 허용한다면, @CrossOrigin 으로 해결 가능
    // But! 인증이 필요한 요청까지 허용하려면, 필터를 직접 설정해주어야 한다.
//    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 내 서버가 응답을 할 때, json을 javascript에서 처리할 수 있게 할지를 설정
        config.addAllowedHeader("*"); //모든 header에 응답을 허용하겠다.
        config.addAllowedOriginPattern("*"); //모든 ip에 응답을 허용하겠다
        config.addAllowedMethod("*"); //모든 http 메서드 요청을 허용하겠다.
        config.addExposedHeader("access_token");
        config.addExposedHeader("refresh_token");
        config.addExposedHeader("Set-Cookie");

        source.registerCorsConfiguration("/api/**",config);

        return new CorsFilter(source);
    }
}
