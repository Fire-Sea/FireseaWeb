package firesea.testserver.service;

import firesea.testserver.domain.TextMessageTitleDto;
import firesea.testserver.domain.basic.PageCustomDto;
import firesea.testserver.repository.SearchingRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SearchingService {

    private final SearchingRepositoryImpl searchRepository;

    public PageCustomDto<TextMessageTitleDto> search(String option, String content, Pageable pageable) {
        Page<TextMessageTitleDto> list = null;

        if (option.equals("textMessage")) {
            list = searchRepository.searchByTextMessage(content, pageable);
        }

        if (option.equals("nickname")) {
            list = searchRepository.searchByNickname(content, pageable);
        }

        if (list != null) {
           return new PageCustomDto<TextMessageTitleDto>(list.getContent(), list.getTotalPages(), list.getTotalElements(), list.getSize());
        }

        return null;
    }

}
