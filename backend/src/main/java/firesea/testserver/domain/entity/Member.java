package firesea.testserver.domain.entity;

import firesea.testserver.domain.basic.JpaBaseTimeEntity;
import lombok.*;
import org.springframework.data.domain.Persistable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(exclude = "textList")
public class Member extends JpaBaseTimeEntity implements Persistable<String> {

    @Id
    String username;

    String password;

    String email;

    String nickname;

    String refreshToken;

    int cnt;

    @OneToMany(mappedBy = "member")
    List<TextMessage> textList = new ArrayList<>();


    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Member(String username, String password, String email, String nickname) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.cnt = 0;
    }


    /*
    의존관계 편의 메서드
     */
    public TextMessage createTextMessage(TextMessage textMessage) {
        this.textList.add(textMessage);
        this.cnt++;
        textMessage.updateMember(this);
        return textMessage;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }


    /*
     * 새로운 id를 구분할 수 있게 해준다
     */
    @Override
    public String getId() {
        return this.getId();
    }

    @Override
    public boolean isNew() {
        return (this.getCreatedTime() == null);
    }


    public void deleteTextMessage(TextMessage textMessage) {
        this.cnt--;
        this.textList.remove(textMessage);
        textMessage.delete();
    }
}
