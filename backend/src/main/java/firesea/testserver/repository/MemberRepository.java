package firesea.testserver.repository;

import firesea.testserver.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String>, MemberRepositoryCustom {

    public int countMemberByUsername(String username);

    public Member findMemberByUsername(String username);

    int countMemberByNickname(String nickname);

}
