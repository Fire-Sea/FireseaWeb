package firesea.testserver.domain.basic;

import lombok.Data;

import java.util.List;

@Data
public class PageCustomDto<T> {
    List<T> content;
    int totalPages;
    long totalElements;
    int size;

    public PageCustomDto(List<T> content, int totalPages, long totalElements, int size) {
        this.content = content;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.size = size;
    }

}
