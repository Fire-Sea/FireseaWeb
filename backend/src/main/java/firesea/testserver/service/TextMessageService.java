package firesea.testserver.service;

import firesea.testserver.domain.*;
import firesea.testserver.domain.basic.PageCustomDto;
import firesea.testserver.domain.entity.Member;
import firesea.testserver.domain.entity.TextMessage;
import firesea.testserver.repository.MemberRepository;
import firesea.testserver.repository.TextMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TextMessageService {

    private final TextMessageRepository textMessageRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void save(TextMessage textMessage, String username) {

        Member member = memberRepository.findMemberByUsername(username);
        TextMessage textMessageByMember = member.createTextMessage(textMessage);

        textMessageRepository.save(textMessageByMember);
    }


    public Page<TextMessage> findAll(Pageable pageable) {
        return textMessageRepository.findAll(pageable);
    }

    public PageCustomDto<TextMessageTitleDto> getMainPageList(String category, Pageable pageable) {
        Page<TextMessageTitleDto> mainPageList = textMessageRepository.getMainPageList(category, pageable);
        List<TextMessageTitleDto> content = mainPageList.getContent();
        int totalPages = mainPageList.getTotalPages();
        long totalElements = mainPageList.getTotalElements();
        int size = mainPageList.getSize();

        log.info("totalPage = {}", totalPages);
        log.info("totalElements = {}", totalElements);
        log.info("size = {}", size);
        for (TextMessageTitleDto dto : content) {
            log.info("dto = {} ", dto.getTextTitle());
            log.info("nickname = {}", dto.getNickname());

        }

        PageCustomDto<TextMessageTitleDto> customPage = new PageCustomDto<>(content, totalPages, totalElements, size);

        return customPage;
    }

    @Transactional
    public TextMessage detailTextMessage(String category, int id) {

        
//        TextMessage byCategoryAndId = textMessageRepository.findByCategoryAndId(category, id);

        //페치 조인 적용
        TextMessage textMessage = textMessageRepository.findDetailTextMessage(id);
        textMessage.increaseViews();

//        Member member = byCategoryAndId.getMember();
//        log.info("member.username = {}", member.getUsername());
//        log.info("member.nickname = {}", member.getNickname());
//
//        log.info("memebr = {}", byCategoryAndId.getMember());

        return textMessage;
    }

    @Transactional
    public void update(int id, String textTitle, String textBody) {
        TextMessage textMessage = textMessageRepository.findById(id).get();
        textMessage.updateTextMessage(textTitle, textBody);


    }

    @Transactional
    public void delete(String username, int id) {
        TextMessage textMessage = textMessageRepository.findById(id).get();
        Member savedMember = memberRepository.findMemberByUsername(username);
        savedMember.deleteTextMessage(textMessage);

    }

    public PageCustomDto<UserTextMessageTitleDto> getUserTmList(String username, Pageable pageable) {
        Page<UserTextMessageTitleDto> userTmList = memberRepository.getUserTmList(username, pageable);
       return new PageCustomDto<>(
                userTmList.getContent(), userTmList.getTotalPages(), userTmList.getTotalElements(), userTmList.getSize()
        );
    }


}

