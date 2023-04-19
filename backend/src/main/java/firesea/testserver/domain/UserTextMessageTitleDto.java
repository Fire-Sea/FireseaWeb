package firesea.testserver.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTextMessageTitleDto {
    public int id;
    public String textTitle;
    public LocalDateTime createdTime;
    public String nickname;
    public String category;
    public int views;
    public int likes;
    public int dislikes;
    public int commentCnt;
}
