package firesea.testserver.service;

import firesea.testserver.domain.entity.LikeAndDislike;
import firesea.testserver.domain.entity.TextMessage;
import firesea.testserver.error.DuplicateLike;
import firesea.testserver.repository.LikeAndDislikeRepository;
import firesea.testserver.repository.TextMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)

public class LikeAndDislikeService {

    private final int LIKE = 0;
    private final int DISLIKE = 1;

    private final LikeAndDislikeRepository likeAndDislikeRepository;
    private final TextMessageRepository textMessageRepository;

    @Transactional
    public int likeTm(int tmId, String ip) {
        int cnt = 0;
        boolean success = checkAndUpdateLd(tmId, ip, LIKE);

        if (success) {
            cnt =  countTmLikes(tmId);
        }
        return cnt;
    }


    @Transactional
    public int dislikeTm(int tmId, String ip) {
        int cnt = 0;
        boolean success = checkAndUpdateLd(tmId, ip, DISLIKE);

        if (success) {
            cnt =  countTmDislikes(tmId);
        }
        return cnt;
    }



    public int countTmLikes(int id) {
        TextMessage textMessage = textMessageRepository.findById(id).get();
        return likeAndDislikeRepository.countTmLikes(textMessage);
    }

    public int countTmDislikes(int id) {
        TextMessage textMessage = textMessageRepository.findById(id).get();
        return likeAndDislikeRepository.countTmDislikes(textMessage);
    }




    public boolean checkAndUpdateLd(int tmId, String ip , int code) {

        LikeAndDislike savedLd = likeAndDislikeRepository.findByTmIdAndIp(tmId, ip);
        TextMessage tm = textMessageRepository.findById(tmId).get();


        if (savedLd == null && code == LIKE) {
            LikeAndDislike newLd = new LikeAndDislike(ip, tm, true, false);
            tm.increaseLike();
            likeAndDislikeRepository.save(newLd);
            return true;
        }

        if (savedLd == null && code == DISLIKE) {
            LikeAndDislike newLd = new LikeAndDislike(ip, tm, false, true);
            tm.increaseDislike();
            likeAndDislikeRepository.save(newLd);
            return true;
        }

        if (!savedLd.isLikeTrue() && code == LIKE) {
            savedLd.increaseLike(tm);
            return true;
        }

        if (!savedLd.isDislikeTrue() && code == DISLIKE) {
            savedLd.increaseDislike(tm);
            return true;
        }

        if (savedLd.isLikeTrue() && code == LIKE) {
            throw new DuplicateLike();
        }

        if (savedLd.isDislikeTrue() && code == DISLIKE) {
            throw new DuplicateLike();
        }

        return false;
    }

}
