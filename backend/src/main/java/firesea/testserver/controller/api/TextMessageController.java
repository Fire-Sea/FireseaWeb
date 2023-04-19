package firesea.testserver.controller.api;

import firesea.testserver.domain.*;
import firesea.testserver.domain.basic.DefaultRes;
import firesea.testserver.domain.basic.PageCustomDto;
import firesea.testserver.domain.entity.TextMessage;
import firesea.testserver.service.TextMessageService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TextMessageController {
    private final TextMessageService textMessageService;

    @PostMapping("/api/user/send")
    public DefaultRes<TextMessage> send(@RequestBody TextMessageDto textMessageDto, HttpServletRequest request) {
        log.info("textMessage.title = {}, textMessage.body={}, category={}", textMessageDto.getTextTitle(), textMessageDto.getTextBody(), textMessageDto.getCategory());

        //token에서 가져온 데이터
        String username = (String) request.getAttribute("username");
        String nickname = (String) request.getAttribute("nickname");

        TextMessage textMessage = new TextMessage(textMessageDto.getTextTitle(), textMessageDto.getTextBody(), textMessageDto.getCategory());
        textMessageService.save(textMessage, username);

        return DefaultRes.res(20000, "저장 완료");
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    static class TextMessageDto {
        String textTitle;
        String textBody;
        String category;
    }


    @GetMapping("/api/list")
    public PageCustomDto<TextMessageTitleDto> list(@RequestParam String category, @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        log.info("페이징 시작");
        PageCustomDto<TextMessageTitleDto> mainPageList = textMessageService.getMainPageList(category, pageable);
        return mainPageList;
    }

    @GetMapping("/api/user/list")
    public PageCustomDto<UserTextMessageTitleDto> userTmList(HttpServletRequest request, @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        String username = (String) request.getAttribute("username");
        PageCustomDto<UserTextMessageTitleDto> userTmList = textMessageService.getUserTmList(username, pageable);
        return userTmList;
    }

    @GetMapping("/api/detail")
    public TextMessageDetailDto detail(@RequestParam String category, int id) {
        log.info("글 자세히 보기");
        TextMessage textMessage = textMessageService.detailTextMessage(category, id);
//        String nickname = textMessage.getMember().getNickname(); //강제 초기화

        TextMessageDetailDto detailDto = TextMessageDetailDto.builder()
                .id(textMessage.getId())
                .textTitle(textMessage.getTextTitle())
                .textBody(textMessage.getTextBody())
                .createdTime(textMessage.getCreatedTime())
                .nickname(textMessage.getMember().getNickname())
                .views(textMessage.getViews())
                .likes(textMessage.getLikes())
                .dislikes(textMessage.getDislikes())
                .build();

        return detailDto;
    }


    @Data
    @Builder
    static class TextMessageDetailDto {
        public int id;
        public String textTitle;

        public String textBody;
        public LocalDateTime createdTime;
        public String nickname;
        public int views;
        public int likes;
        public int dislikes;

    }


    @PatchMapping("/api/user/update")
    public DefaultRes update(@RequestParam int id, HttpServletRequest request, @RequestBody TextMessageDto dto) {
        textMessageService.update(id, dto.getTextTitle(), dto.getTextBody());
        return DefaultRes.res(20015, "업데이트 성공");
    }

    @DeleteMapping("/api/user/delete")
    public DefaultRes delete(@RequestParam int id, HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        textMessageService.delete(username, id);
        return DefaultRes.res(20016, "삭제 성공");
    }





}
