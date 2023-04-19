package firesea.testserver.repository;

import firesea.testserver.domain.UserTextMessageTitleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberRepositoryCustom {

//    int changeNickname(String username, String nickname);
    int findCnt(String username);

    Page<UserTextMessageTitleDto> getUserTmList(String username, Pageable pageable);
}
