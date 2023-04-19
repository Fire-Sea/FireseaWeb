package firesea.testserver.repository;

import firesea.testserver.domain.CommentDetailDto;
import firesea.testserver.domain.entity.TextMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentRepositoryCustom {

    Page<CommentDetailDto> findCommentListByTm(TextMessage textMessage, Pageable pageable);
}
