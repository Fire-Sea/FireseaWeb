package firesea.testserver.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import firesea.testserver.domain.basic.DefaultRes;
import firesea.testserver.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static firesea.testserver.jwt.JwtConstants.*;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationInterceptor implements HandlerInterceptor {


    private final MemberService memberService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURL().toString();
        String authorization = request.getHeader("Authorization");


        log.info("usl = {}" , request.getRequestURL().toString());
        log.info("content type = {}", request.getContentType());
        log.info("인터셉터 발동");

        //authorization header 가 없음
        if (authorization == null) {

            log.info("인터셉터 null");
            new ObjectMapper().writeValue(response.getOutputStream(), DefaultRes.res(40005, "access token 존재하지 않습니다. 로그인 후 이용할 수 있습니다."));
            return false;
        }

        try {

            String accessToken = authorization.replace(TOKEN_HEADER_PREFIX, "");
            log.info("access token 받은 것 = {}", TOKEN_HEADER_PREFIX + accessToken);
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(JWT_SECRET_AT)).build();
            DecodedJWT decodedJWT = verifier.verify(accessToken);

            String username = decodedJWT.getClaim("username").asString();
            String nickname = decodedJWT.getClaim("nickname").asString();
            request.setAttribute("username", username);
            request.setAttribute("nickname", nickname);

            log.info("인터셉터 정상작동 통과");

        } catch (TokenExpiredException e) {
            log.info("인터셉터 기간만료");

            new ObjectMapper().writeValue(response.getOutputStream(), DefaultRes.res(40006, "access token이 만료되었습니다. refresh token을 통해 access token을 재발급 받으십시오. redirect 주소 => '/api/refreshToken/' "));
            return false;
        } catch (Exception e) {
            log.info("인터셉터 예외");

            new ObjectMapper().writeValue(response.getOutputStream(), DefaultRes.res(40007, "access toke이 유효하지 않습니다."));
            return false;
        }


        return true;
    }
}
