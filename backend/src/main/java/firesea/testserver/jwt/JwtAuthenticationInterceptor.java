package firesea.testserver.jwt;

/*
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationInterceptor implements HandlerInterceptor {

   // 로그인이 완료된 이후에 access token 과 refresh token 을 발급해 줄 것이다
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        Optional<Map> optionalMap = (Optional<Map>) request.getAttribute("optionalMap");
        if(!optionalMap.isEmpty()){
            failedLogin(response);
        }else{
            successedLogin(optionalMap.get(), response);
        }
    }

    void failedLogin(HttpServletResponse response) throws Exception{
        log.info("로그인에 실패했습니다");
        DefaultRes defaultRes =  DefaultRes.res(40002, "로그인에 실패했습니다");
        new ObjectMapper().writeValue(response.getOutputStream(), defaultRes);
    }

    void successedLogin(Map memberAttribute , HttpServletResponse response) throws Exception{
        log.info("로그인에 성공했습니다");

        //access token 생성
        String accessToken = JWT.create()
                .withSubject("access token")
                .withExpiresAt(new Date(System.currentTimeMillis() + AT_EXP_TIME))
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withClaim("username", (String)memberAttribute.get("username"))
                .withClaim("nickname", (String)memberAttribute.get("nickname"))
                .sign(Algorithm.HMAC256(JWT_SECRET_AT));
        log.info("accessToken = {}", accessToken);
        //응답 헤더에 access token 추가
        response.addHeader(AT_HEADER, TOKEN_HEADER_PREFIX + accessToken);

        //refresh Token 생성
        String refreshToken = JWT.create()
                .withSubject("refresh token")
                .withExpiresAt(new Date(System.currentTimeMillis() + RT_EXP_TIME))
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withClaim("username",(String) memberAttribute.get("username"))
                .withClaim("nickname",(String) memberAttribute.get("nickname"))
                .sign(Algorithm.HMAC256(JWT_SECRET_RT));
        log.info("refreshToken = {}", refreshToken);

        //응답 헤더에 refresh token 추가
        response.addHeader(RT_HEADER, TOKEN_HEADER_PREFIX+ refreshToken);

        //refresh token member db에 저장?

//        Map<String, String> map = new HashMap<>();
//        map.put(AT_HEADER, accessToken);
//        map.put(RT_HEADER, refreshToken);
//        response.setCharacterEncoding("utf-8");
//        new ObjectMapper().writeValue(response.getOutputStream(), map);

        DefaultRes defaultRes =  DefaultRes.res(20002, "로그인 성공", memberAttribute);
        response.setCharacterEncoding("utf-8");
        new ObjectMapper().writeValue(response.getOutputStream(), defaultRes);

    }
}
*/