package firesea.testserver.repository;

import firesea.testserver.domain.entity.LikeAndDislike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeAndDislikeRepository extends JpaRepository<LikeAndDislike, Integer>, LikeAndDislikeRepositoryCustom {

    LikeAndDislike  findByTmId(int tmId);
    LikeAndDislike  findByTmIdAndIp(int tmId, String ip);


}
