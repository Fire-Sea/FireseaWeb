package firesea.testserver.error;

import firesea.testserver.domain.basic.DefaultRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ErrorControllerAdvice {


    @ExceptionHandler(DuplicateNickname.class)
    public DefaultRes duplicateNickname(DuplicateNickname e) {
        log.error("[duplicate nickname]", e);
        return DefaultRes.res(40017, e.getMessage());
    }

    @ExceptionHandler(DuplicateLike.class)
    public DefaultRes duplicateLike(DuplicateLike duplicateLike) {
        return DefaultRes.res(40018, "이미 좋아요를 눌렀습니다");
    }

    @ExceptionHandler(DuplicateDislike.class)
    public DefaultRes duplicateDisLike(DuplicateDislike duplicateDislike) {
        return DefaultRes.res(40019, "이미 싫어요를 눌렀습니다");
    }
}
