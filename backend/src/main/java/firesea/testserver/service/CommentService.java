package firesea.testserver.service;

import firesea.testserver.domain.CommentDetailDto;
import firesea.testserver.domain.basic.PageCustomDto;
import firesea.testserver.domain.entity.Comment;
import firesea.testserver.domain.entity.Member;
import firesea.testserver.domain.entity.TextMessage;
import firesea.testserver.repository.CommentRepository;
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
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final TextMessageRepository textMessageRepository;

    @Transactional
    public void save(String username, int id, String commentBody) {

        Member savedMember = memberRepository.findMemberByUsername(username);
        TextMessage savedTm = textMessageRepository.findById(id).get();

        Comment comment = new Comment(savedMember, savedTm, commentBody);
        commentRepository.save(comment);

        savedTm.increaseCommentCnt();
    }

    public PageCustomDto<CommentDetailDto> getList(int id, Pageable pageable) {

        TextMessage savedTm = textMessageRepository.findById(id).get();
        Page<CommentDetailDto> commentListByTm = commentRepository.findCommentListByTm(savedTm, pageable);

        List<CommentDetailDto> content = commentListByTm.getContent();
        long totalElements = commentListByTm.getTotalElements();
        int totalPages = commentListByTm.getTotalPages();
        int size = commentListByTm.getSize();
        return new PageCustomDto<>(content, totalPages, totalElements, size);


    }
    @Transactional
    public void updateComment(int commentId, String commentBody) {

        Comment comment = commentRepository.findById(commentId).get();
        comment.updateCommentBody(commentBody);

    }
    @Transactional
    public void deleteComment(int commentId) {
        Comment comment = commentRepository.findById(commentId).get();
        TextMessage textMessage = comment.getTextMessage();
        textMessage.deleteComment(comment);

    }
}
