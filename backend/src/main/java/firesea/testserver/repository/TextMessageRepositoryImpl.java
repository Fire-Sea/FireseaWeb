package firesea.testserver.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import firesea.testserver.domain.*;
import firesea.testserver.domain.entity.QTextMessage;
import firesea.testserver.domain.entity.TextMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static firesea.testserver.domain.entity.QMember.*;
import static firesea.testserver.domain.entity.QTextMessage.*;

@Slf4j
public class TextMessageRepositoryImpl implements TextMessageRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public TextMessageRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public TextMessage findDetailTextMessage(int id) {
        TextMessage textMessage = queryFactory
                .selectFrom(QTextMessage.textMessage)
                .join(QTextMessage.textMessage.member, member).fetchJoin()
                .where(QTextMessage.textMessage.id.eq(id))
                .fetchOne();
        return textMessage;

    }

    @Override
    public Page<TextMessageTitleDto> getMainPageList(String category, Pageable pageable) {
        List<TextMessageTitleDto> content = queryFactory
                .select(Projections.bean(TextMessageTitleDto.class,
                        textMessage.id.as("id"), textMessage.textTitle, textMessage.createdTime,
                        member.nickname, textMessage.views, textMessage.likes , textMessage.dislikes, textMessage.commentCnt
                ))
                .from(textMessage)
                .join(textMessage.member, member)
                .where(
                        textMessage.category.eq(category),
                        textMessage.deleteTrue.eq(false)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(textMessage.id.desc())
                .fetch();

        for (TextMessageTitleDto dto : content) {
            log.info("textMessageDto = {}", dto.getTextTitle());
        }
        // category 별 리스트의 총 갯수
        JPAQuery<Long> countQuery = queryFactory
                .select(textMessage.count())
                .from(textMessage)
                .where(
                        textMessage.category.eq(category),
                        textMessage.deleteTrue.eq(false)
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);

    }


}
