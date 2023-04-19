package firesea.testserver.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import firesea.testserver.domain.entity.Member;
import firesea.testserver.jwt.JwtFactory;
import firesea.testserver.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static firesea.testserver.jwt.JwtConstants.*;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TokenService {

    private final MemberRepository memberRepository;
    private final JwtFactory jwtFactory;
    @Transactional
    public Map refresh(String authorization) {
        String refreshToken = authorization.replace(TOKEN_HEADER_PREFIX, "");;

        DecodedJWT decodedJWT;
        Map<String,String> result = new HashMap<>();
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(JWT_SECRET_RT)).build();
            decodedJWT = verifier.verify(refreshToken);
        } catch (TokenExpiredException e) {
            result.put("failByExpiration", "refresh token이 만료되었습니다. 다시 로그인해야 합니다");
            return result;
        } catch (Exception es) {
            result.put("failByValidation", "refresh token이 유효하지 않습니다. 다시 로그인해야 합니다");
            return result;
        }

        String username = decodedJWT.getClaim("username").asString();
        Member member = memberRepository.findMemberByUsername(username);
        String savedRefreshToken = member.getRefreshToken();

        if (!savedRefreshToken.equals(refreshToken)) {
            result.put("failByDB", "refresh token이 일치하지 않습니다. 다시 로그인해야 합니다");
            return result;
        }

        //refresh token이 유효한 경우(만료되지 않았고 값이 정확한 경우)
        String accessToken = jwtFactory.makeAT(username, member.getNickname()); //access token 재발급
        result.put(AT_HEADER, accessToken);

        //refreshToken의 유효기간이 얼마 남지 않은 경우, refersh token을 재발급해준다
        long refreshExpiresTime = decodedJWT.getClaim("exp").asLong() * 1000;
        long diffDays = (refreshExpiresTime - System.currentTimeMillis()) / 1000 / (24 * 3600); //남은 날짜
        long diffMin = (refreshExpiresTime - System.currentTimeMillis())/ 1000/ 60;

        if (diffMin < 5) {
            String newRefreshToken = jwtFactory.makeRT(username, member.getNickname());
            result.put(RT_HEADER, newRefreshToken);

            member.updateRefreshToken(newRefreshToken); //변경감지
        }

        return result;
    }

    @Transactional
    public void updateRefreshTokens(String username, String refreshToken) {

        Member member = memberRepository.findMemberByUsername(username);
        member.updateRefreshToken(refreshToken); //변경감지
    }

}
