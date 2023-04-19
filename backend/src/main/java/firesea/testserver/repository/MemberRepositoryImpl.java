package firesea.testserver.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import firesea.testserver.domain.UserTextMessageTitleDto;
import firesea.testserver.domain.entity.QMember;
import firesea.testserver.domain.entity.QTextMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import java.util.List;

import static firesea.testserver.domain.entity.QMember.*;
import static firesea.testserver.domain.entity.QTextMessage.*;


@Slf4j
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    MemberRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public int findCnt(String username) {


        Integer cnt = queryFactory
                .select(member.cnt)
                .from(member)
                .where(member.username.eq(username))
                .fetchOne();
        return cnt;
    }

    @Override
    public Page<UserTextMessageTitleDto> getUserTmList(String username, Pageable pageable) {
        List<UserTextMessageTitleDto> list = queryFactory
                .select(Projections.bean(UserTextMessageTitleDto.class,
                        textMessage.id, textMessage.textTitle, textMessage.createdTime,
                        member.nickname, textMessage.category, textMessage.views, 
                        textMessage.likes, textMessage.dislikes, textMessage.commentCnt ))
                .from(textMessage)
                .join(textMessage.member, member)
                .where(
                        member.username.eq(username),
                        textMessage.deleteTrue.eq(false)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(textMessage.id.desc())
                .fetch();

        int cnt = queryFactory
                .select(member.cnt)
                .from(member)
                .where(
                        member.username.eq(username)
                )
                .fetchOne();


        return new PageImpl<UserTextMessageTitleDto>(list, pageable, cnt);
    }
}
