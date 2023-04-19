package firesea.testserver.controller.api;

import firesea.testserver.domain.basic.DefaultRes;
import firesea.testserver.domain.entity.Member;
import firesea.testserver.jwt.JwtFactory;
import firesea.testserver.service.MemberService;
import firesea.testserver.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

import static firesea.testserver.jwt.JwtConstants.*;
import static firesea.testserver.jwt.JwtConstants.TOKEN_HEADER_PREFIX;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberInfoController {

    private final MemberService memberService;
    private final TokenService tokenService;
    private final JwtFactory jwtFactory;

    @PostMapping("/api/register")
    public DefaultRes register(@RequestBody Member member) {
        log.info("member.username = {}, member.email={}, member.password={}",
                member.getUsername(), member.getEmail(), member.getPassword());

        DefaultRes defaultRes;
        int result = memberService.save(member);
        if (result == 1) {
            defaultRes = DefaultRes.res(20001, "회원가입이 완료되었습니다");
            log.info("회원가입 완료");
        } else {
            defaultRes = DefaultRes.res(40001, "회원가입이 실패했습니다");
            log.info("회원가입 실패");
        }
        return defaultRes;
    }

    @PostMapping("/api/login")
    public DefaultRes login(@RequestBody Member member, HttpServletResponse response) {
        log.info("member.username = {}, member.email={}, member.password={}",
                member.getUsername(), member.getEmail(), member.getPassword());

        Optional<Map> optionalMap = memberService.login(member);

        if (optionalMap.isEmpty()) {
            return DefaultRes.res(40002, "로그인에 실패했습니다");
        }

        String username = (String) optionalMap.get().get("username");
        String nickname = (String) optionalMap.get().get("nickname");

        String accessToken = jwtFactory.makeAT(username, nickname);//access token 생성
        response.addHeader(AT_HEADER, TOKEN_HEADER_PREFIX + accessToken); //응답 헤더에 access token 추가

        String refreshToken = jwtFactory.makeRT(username, nickname); //refresh Token 생성
        response.addHeader(RT_HEADER, TOKEN_HEADER_PREFIX + refreshToken); //응답 헤더에 refresh token 추가

        tokenService.updateRefreshTokens(username, refreshToken);//refresh token member db에 저장

        // cookie에 한 번 담아보자
        Cookie cookie = new Cookie("refresh_token", refreshToken);
        cookie.setMaxAge(7 * 24 * 60 * 60);

//        cookie.setSecure(true);
//        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);


        DefaultRes defaultRes = DefaultRes.res(20002, "로그인 성공", nickname);
        return defaultRes;
    }

    @GetMapping("/api/idCheck")
    public DefaultRes idCheck(@RequestParam("username") String username) {
        log.info("member.getUsername = {}", username);

        int count = memberService.countMemberByUsername(username);
        DefaultRes defaultRes;

        if (count == 0) {
            defaultRes = DefaultRes.res(20003, "사용가능한 아이디입니다");
        } else {
            defaultRes = DefaultRes.res(40003, "사용 중인 아이디입니다");
        }

        return defaultRes;
    }

    @GetMapping("/api/nicknameCheck")
    public DefaultRes nicknameCheck(@RequestParam("nickname") String nickname) {
        log.info("member.getNickname = {}", nickname);
        int count = memberService.countMemberByNickname(nickname);

        DefaultRes defaultRes;

        if (count == 0) {
            defaultRes = DefaultRes.res(20004, "사용가능한 닉네임입니다");
        } else {
            defaultRes = DefaultRes.res(40004, "사용 중인 닉네임입니다");
        }

        return defaultRes;
    }

    @GetMapping("/api/user/changeNickname")
    public DefaultRes changeNickname(@RequestParam String newNickname, HttpServletRequest request) {

        String username = (String) request.getAttribute("username");

        log.info("username = {}", username);
        log.info("nickname = {}", newNickname);
        boolean success = memberService.changeNickname(username, newNickname);
        return DefaultRes.res(20017, "닉네임 변경 성공", newNickname);
    }

    @GetMapping("/api/user/tmCnt")
    public DefaultRes<Integer> getTmCnt(HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        int cnt = memberService.getTmCnt(username);

        return DefaultRes.res(20018, "글 갯수", cnt);
    }



    @GetMapping("/api/user/test")
    public String test(HttpServletRequest request) {

        String requestURI = request.getRequestURI();

        return "success :  " + requestURI;
    }


    @GetMapping("/cookie/text")
    public String test2(HttpServletRequest request, HttpServletResponse response) {

        String requestURI = request.getRequestURI();


        // cookie에 한 번 담아보자
        Cookie cookie = new Cookie("refresh_token", "goodcookie");
        cookie.setMaxAge(7 * 24 * 60 * 60);

//        cookie.setSecure(true);
//        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);


        return "success :  " + requestURI;
    }
}
