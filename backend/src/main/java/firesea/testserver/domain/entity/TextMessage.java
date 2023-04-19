package firesea.testserver.domain.entity;

import firesea.testserver.domain.basic.JpaBaseTimeEntity;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Slf4j
public class TextMessage extends JpaBaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "textId")
    int id;

    String textTitle;

    String textBody;

    String category;

    Boolean deleteTrue = false;

    LocalDateTime deleteTime = null;

    int views;

    int likes;
    int dislikes;

    int commentCnt;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="member_username")
    Member member;

    public TextMessage(String textTitle, String textBody, String category) {
        this.textTitle = textTitle;
        this.textBody = textBody;
        this.category = category;
        this.deleteTrue = false;
        this.deleteTime = null;
        this.views = 0;
        this.likes = 0;
        this.dislikes = 0;
    }



    /**
     * 편의 메서드
     */
    public void updateMember(Member member) {
        this.member = member;
    }

    public void updateTextMessage(String textTitle, String textBody) {

        this.textTitle = textTitle;
        this.textBody = textBody;
    }

    public void delete() {
        this.deleteTrue = true;
        this.deleteTime = LocalDateTime.now();
    }

    public void increaseViews() {
        this.views++;
    }

    public void increaseLike() {
        this.likes++;
        log.info("likes= {}", likes);
    }

    public void increaseDislike() {
        this.dislikes++;
        log.info("dislikes = {}",dislikes);

    }

    public void increaseCommentCnt() {
        this.commentCnt++;
    }

    public void deleteComment(Comment comment) {
        this.commentCnt--;
        comment.deleteComment();
    }
}

