package firesea.testserver.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import firesea.testserver.domain.TextMessageTitleDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static firesea.testserver.domain.entity.QMember.*;
import static firesea.testserver.domain.entity.QMember.member;
import static firesea.testserver.domain.entity.QTextMessage.*;
import static firesea.testserver.domain.entity.QTextMessage.textMessage;

@Slf4j
@Repository
public class SearchingRepositoryImpl {

    private final JPAQueryFactory queryFactory;

    public SearchingRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    public Page<TextMessageTitleDto> searchByTextMessage(String content, Pageable pageable) {

        log.info("content = {}",content);

        List<TextMessageTitleDto> list = queryFactory
                .select(
                        Projections.bean(TextMessageTitleDto.class,
                                textMessage.id, textMessage.textTitle, textMessage.createdTime,
                                member.nickname, textMessage.views, textMessage.likes,
                                textMessage.dislikes, textMessage.commentCnt))
                .from(textMessage)
                .join(textMessage.member, member)
                .where(checkContent(content))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(textMessage.id.desc())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(textMessage.count())
                .from(textMessage)
                .join(textMessage.member, member)
                .where(checkContent(content));

        return PageableExecutionUtils.getPage(list, pageable, countQuery::fetchOne);
    }
    private BooleanExpression checkContent(String content) {

//        String[] words = content.split(" ");
        String[] words = content.split("[`~!@#$%^&*()_|+\\-=?;:'\",.<>\\{\\}\\[\\]\\\\\\/ ]");
        for (String word : words) {
            log.info("[searching repository ]word = {}", word);
        }

        BooleanExpression contains = textMessage.textTitle.concat(textMessage.textBody).contains(words[0]);
        log.info("words[0]={}", words[0]);

        if (words.length == 1) { // 길이가 1 => 검색하는 단어가 한 개
            return contains;
        } else { // 길이가 2 이상 => 검색하는 단어가 여러 개
            for (int i = 1; i<words.length ; i++) {
                log.info("words[i]={}", words[i]);
                contains = contains.and(textMessage.textTitle.concat(textMessage.textBody).contains(words[i]));
            }
        }
        return contains;
//        String[] split = content.split("[^\uAC00-\uD7A3xfe0-9a-zA-Z)]");
    }

    public Page<TextMessageTitleDto> searchByNickname(String content, Pageable pageable) {

        List<TextMessageTitleDto> list = queryFactory
                .select(
                        Projections.bean(TextMessageTitleDto.class,
                                textMessage.id, textMessage.textTitle, textMessage.createdTime,
                                member.nickname, textMessage.views, textMessage.likes,
                                textMessage.dislikes, textMessage.commentCnt))
                .from(textMessage)
                .join(textMessage.member, member)
                .where(checkNickname(content))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(textMessage.id.desc())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(textMessage.count())
                .from(textMessage)
                .join(textMessage.member, member)
                .where(checkContent(content));

        return PageableExecutionUtils.getPage(list, pageable, countQuery::fetchOne);
    }

    private BooleanExpression checkNickname(String content) {

//        String[] words = content.split(" ");
        String[] words = content.split("[^\uAC00-\uD7A3xfe0-9a-zA-Z)]");

        BooleanExpression contains = member.nickname.contains(words[0]);
        log.info("words[0]={}", words[0]);

        if (words.length == 1) { // 길이가 1 => 검색하는 단어가 한 개
            return contains;
        } else { // 길이가 2 이상 => 검색하는 단어가 여러 개
            for (int i = 1; i<words.length ; i++) {
                log.info("words[i]={}", words[i]);
                contains = contains.and(member.nickname.contains(words[i]));
            }
        }
        return contains;
    }
}
