package firesea.testserver.repository;

import firesea.testserver.domain.entity.TextMessage;

public interface LikeAndDislikeRepositoryCustom {

    int countTmLikes(TextMessage textMessage);
    int countTmDislikes(TextMessage textMessage);

    boolean checkDuplicateLike(String ip, TextMessage textMessage);
    boolean checkDuplicateDislike(String ip, TextMessage textMessage);
}
