package firesea.testserver.repository;

import firesea.testserver.domain.entity.TextMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TextMessageRepository extends JpaRepository<TextMessage, Integer>, TextMessageRepositoryCustom {

    Page<TextMessage> findByCategory(String category, Pageable pageable);

    TextMessage findByCategoryAndId(String category, int id);
}
