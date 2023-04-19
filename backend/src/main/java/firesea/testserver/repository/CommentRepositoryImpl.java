package firesea.testserver.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import firesea.testserver.domain.CommentDetailDto;
import firesea.testserver.domain.entity.TextMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;

import java.util.List;

import static firesea.testserver.domain.entity.QComment.*;
import static firesea.testserver.domain.entity.QMember.*;

public class CommentRepositoryImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    CommentRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Page<CommentDetailDto> findCommentListByTm(TextMessage textMessage, Pageable pageable) {
        List<CommentDetailDto> list = queryFactory
                .select(Projections.bean(CommentDetailDto.class,
                        comment.id.as("commentId"), member.nickname, comment.createdTime,
                        comment.lastModifiedTime, comment.commentBody)
                )
                .from(comment)
                .join(comment.member, member)
                .where(
                        comment.textMessage.eq(textMessage),
                        comment.deleteTrue.eq(false)
                )
                .orderBy(comment.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(comment.count())
                .from(comment)
                .where(
                        comment.textMessage.eq(textMessage),
                        comment.deleteTrue.eq(false)
                );

        return PageableExecutionUtils.getPage(list, pageable, countQuery::fetchOne);
    }
}
