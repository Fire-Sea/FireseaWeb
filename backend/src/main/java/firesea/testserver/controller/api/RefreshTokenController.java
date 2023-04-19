package firesea.testserver.controller.api;

import firesea.testserver.domain.basic.DefaultRes;
import firesea.testserver.service.MemberService;
import firesea.testserver.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static firesea.testserver.jwt.JwtConstants.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RefreshTokenController {

    private final MemberService memberService;
    private final TokenService tokenService;

    @GetMapping("/api/refresh")
    public DefaultRes refresh(HttpServletRequest request, HttpServletResponse response) {
        String authorization = request.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith(TOKEN_HEADER_PREFIX)) {
            return DefaultRes.res(40008, "refreshToken이 존재하지 않습니다. refresh token을 보내주십시오");
        }

        Map<String, String> result = tokenService.refresh(authorization);
        if (result.containsKey("failByExpiration")) {
            return DefaultRes.res(40009, result.get("failByExpiration"));
        }
        if (result.containsKey("failByValidation")) {
            return DefaultRes.res(40010, result.get("failByValidation"));
        }
        if (result.containsKey("failByDB")) {
            return DefaultRes.res(40011, result.get("failByDB"));
        }

        response.setHeader(AT_HEADER, TOKEN_HEADER_PREFIX + result.get(AT_HEADER));
        if (result.containsKey(RT_HEADER)) {
            response.setHeader(RT_HEADER, TOKEN_HEADER_PREFIX + result.get(RT_HEADER));

            return new DefaultRes(20009, "access token과 refresh token을 재발급하였습니다.");

        }

        return new DefaultRes(20010, "access token을 재발급하였습니다.");
    }

    @GetMapping("/api/user")
    public DefaultRes userTest(HttpServletRequest request) {

        String authorization = request.getHeader("Authorization");
        return DefaultRes.res(20011, "access token 검사 성공");
    }

}
