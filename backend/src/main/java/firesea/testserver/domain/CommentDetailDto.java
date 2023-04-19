package firesea.testserver.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDetailDto {


    int commentId;
    String nickname;
    LocalDateTime createdTime;
    LocalDateTime lastModifiedTime;
    String commentBody;
}
