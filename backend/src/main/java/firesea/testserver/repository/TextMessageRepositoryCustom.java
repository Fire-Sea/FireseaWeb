package firesea.testserver.repository;

import firesea.testserver.domain.entity.TextMessage;
import firesea.testserver.domain.TextMessageTitleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TextMessageRepositoryCustom {

    Page<TextMessageTitleDto> getMainPageList(String category, Pageable pageable);

    TextMessage findDetailTextMessage(int id);

}
