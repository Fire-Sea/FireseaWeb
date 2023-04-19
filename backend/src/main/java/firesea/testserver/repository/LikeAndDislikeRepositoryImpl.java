package firesea.testserver.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import firesea.testserver.domain.entity.TextMessage;

import javax.persistence.EntityManager;

import static firesea.testserver.domain.entity.QLikeAndDislike.*;

public class LikeAndDislikeRepositoryImpl implements LikeAndDislikeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    LikeAndDislikeRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public int countTmLikes(TextMessage textMessage) {

        int cnt =  queryFactory
                .select(likeAndDislike.count())
                .from(likeAndDislike)
                .where(
                        likeAndDislike.tm.eq(textMessage),
                        likeAndDislike.likeTrue.eq(true)
                )
                .fetchOne()
                .intValue();

        return cnt;
    }

    @Override
    public int countTmDislikes(TextMessage textMessage) {

        int cnt =  queryFactory
                .select(likeAndDislike.count())
                .from(likeAndDislike)
                .where(
                        likeAndDislike.tm.eq(textMessage),
                        likeAndDislike.dislikeTrue.eq(true)
                )
                .fetchOne()
                .intValue();

        return cnt;
    }

    @Override
    public boolean checkDuplicateLike(String ip, TextMessage textMessage) {
        boolean duplicate = queryFactory
                .select(likeAndDislike.likeTrue)
                .from(likeAndDislike)
                .where(
                        likeAndDislike.ip.eq(ip),
                        likeAndDislike.tm.eq(textMessage)
                )
                .fetchOne();
        return duplicate;
    }

    @Override
    public boolean checkDuplicateDislike(String ip, TextMessage textMessage) {
        boolean duplicate = queryFactory
                .select(likeAndDislike.dislikeTrue)
                .from(likeAndDislike)
                .where(
                        likeAndDislike.ip.eq(ip),
                        likeAndDislike.tm.eq(textMessage)
                )
                .fetchOne();
        return duplicate;
    }
}
