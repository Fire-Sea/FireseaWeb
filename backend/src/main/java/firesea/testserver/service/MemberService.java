package firesea.testserver.service;

import firesea.testserver.domain.entity.Member;
import firesea.testserver.error.DuplicateNickname;
import firesea.testserver.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public int countMemberByUsername(String username) {
      return  memberRepository.countMemberByUsername(username);
    }

    @Transactional
    public int save(Member member) {
        Member savedMember = memberRepository.save(member);
        if (savedMember == null) {
            return 0;
        } else {
            return 1;
        }
    }


    public int countMemberByNickname(String nickname) {
        return  memberRepository.countMemberByNickname(nickname);
    }


    public Optional<Map> login(Member member) {
        Member savedMember = memberRepository.findMemberByUsername(member.getUsername());

        if (savedMember == null) {
            return Optional.empty();
        }

        log.info("member.getPassword={}, savedMember.getPassword = {}", member.getPassword(), savedMember.getPassword());
        if (member.getPassword().equals(savedMember.getPassword())) {

            String username = savedMember.getUsername();
            String nickname = savedMember.getNickname();
            Map<String, String> map = new HashMap<>();
            map.put("username", username);
            map.put("nickname", nickname);
            log.info(username);
            log.info(nickname);
            return Optional.of(map);
        }

        return Optional.empty();
    }

    @Transactional
    public boolean changeNickname(String username, String newNickname) {

        int i = memberRepository.countMemberByNickname(newNickname);
        if (i != 0) {
            throw new DuplicateNickname("중복된 닉네임. 사용 불가");
        }

        Member savedMember = memberRepository.findMemberByUsername(username);
        savedMember.updateNickname(newNickname);

        return true;
    }

    public int getTmCnt(String username) {
        return memberRepository.findCnt(username);
    }
}
