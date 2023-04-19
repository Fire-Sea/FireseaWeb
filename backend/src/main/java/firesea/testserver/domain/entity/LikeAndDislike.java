package firesea.testserver.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class LikeAndDislike {

    @Id @GeneratedValue
    int id;

    String ip;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tm_id")
    TextMessage tm;

    boolean likeTrue;

    boolean dislikeTrue;


    public LikeAndDislike(String ip, TextMessage tm, boolean likeTrue, boolean dislikeTrue) {
        this.ip = ip;
        this.tm = tm;
        this.likeTrue = likeTrue;
        this.dislikeTrue = dislikeTrue;
    }

    public void increaseLike(TextMessage tm) {
        this.likeTrue = true;
        tm.increaseLike();
        log.info("likeAndDislike");
    }

    public void increaseDislike(TextMessage tm) {
        this.dislikeTrue = true;
        tm.increaseDislike();
        log.info("likeAndDislike");

    }
}
