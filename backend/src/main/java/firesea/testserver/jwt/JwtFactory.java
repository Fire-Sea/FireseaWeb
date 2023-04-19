package firesea.testserver.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

import static firesea.testserver.jwt.JwtConstants.*;

@Component
public class JwtFactory {

    public String makeAT(String username, String nickname) {
        return JWT.create()
                .withSubject("access token")
                .withExpiresAt(new Date(System.currentTimeMillis() + AT_EXP_TIME))
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withClaim("username", username)
                .withClaim("nickname",nickname)
                .sign(Algorithm.HMAC256(JWT_SECRET_AT));

    }

    public String makeRT(String username, String nickname) {
        return JWT.create()
                .withSubject("access token")
                .withExpiresAt(new Date(System.currentTimeMillis() + RT_EXP_TIME))
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withClaim("username", username)
                .withClaim("nickname",nickname)
                .sign(Algorithm.HMAC256(JWT_SECRET_RT));

    }
}
