package firesea.testserver.config;

import firesea.testserver.jwt.JwtAuthorizationInterceptor;
import firesea.testserver.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final MemberService memberService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JwtAuthorizationInterceptor(memberService))
                .order(1)
                .addPathPatterns("/api/user/**")
                .excludePathPatterns("/css/**", "/*.ico", "/error");
    }
}
