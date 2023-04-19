package firesea.testserver.settings;

import firesea.testserver.domain.entity.Member;
import firesea.testserver.domain.entity.TextMessage;
import firesea.testserver.service.MemberService;
import firesea.testserver.service.TextMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitTextMessageList {

    private final TextMessageService textMessageService;
    private final MemberService memberService;

    @PostConstruct
    public void init() {
        memberService.save(new Member("aaaa1111", "11111111", "aaa@naver.com", "백엔드개발자"));
        memberService.save(new Member("bbbb2222", "22222222", "bbb@naver.com", "프론트개발자"));
        memberService.save(new Member("cccc3333", "33333333", "ccc@naver.com", "플로버1"));
        memberService.save(new Member("dddd4444", "44444444", "ddd@naver.com", "플로버2"));
        memberService.save(new Member("eeee5555", "55555555", "eee@naver.com", "플로버3"));
        memberService.save(new Member("ffff6666", "66666666", "fff@naver.com", "플로버4"));
        memberService.save(new Member("gggg7777", "77777777", "ggg@naver.com", "플로버5"));
        memberService.save(new Member("hhhh8888", "88888888", "hhh@naver.com", "플로버6"));
        memberService.save(new Member("iiii9999", "99999999", "iii@naver.com", "플로버7"));
        memberService.save(new Member("jjjj0000", "00000000", "jjj@naver.com", "플로버8"));

        memberService.save(new Member("germs", "germs1020!", "germs1020@gmail.com", "바보"));



        textMessageService.save(new TextMessage("프로미스나인 ", "박지원", Category.SERVER), "aaaa1111");
        textMessageService.save(new TextMessage("프로미스나인 ", "이나경", Category.FRONT), "bbbb2222");
        textMessageService.save(new TextMessage("프로미스나인 ", "노지선", Category.SERVER), "cccc3333");
        textMessageService.save(new TextMessage("프로미스나인 ", "송하영", Category.FRONT), "dddd4444");
        textMessageService.save(new TextMessage("프로미스나인 ", "이채영", Category.SERVER), "eeee5555");
        textMessageService.save(new TextMessage("프로미스나인 ", "이서연", Category.SERVER), "ffff6666");
        textMessageService.save(new TextMessage("프로미스나인 ", "이새롬", Category.FRONT), "gggg7777");
        textMessageService.save(new TextMessage("프로미스나인 ", "백지헌", Category.SERVER), "hhhh8888");
        textMessageService.save(new TextMessage("뉴진스 ", "팜하니", Category.FRONT) , "iiii9999");
        textMessageService.save(new TextMessage("뉴진스 ", "김민지", Category.SERVER), "jjjj0000");
        textMessageService.save(new TextMessage("뉴진스 ", "다니엘", Category.FRONT), "aaaa1111");
        textMessageService.save(new TextMessage("뉴진스 ", "강해린", Category.SERVER), "aaaa1111");
        textMessageService.save(new TextMessage("뉴진스 ", "강혜인", Category.FRONT), "bbbb2222");
        textMessageService.save(new TextMessage("테니스 ", "조코비치", Category.SERVER), "cccc3333");
        textMessageService.save(new TextMessage("테니스 ", "나달", Category.FRONT), "dddd4444");
        textMessageService.save(new TextMessage("테니스 ", "페더러", Category.SERVER), "eeee5555");


    }

}
