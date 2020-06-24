package me.gogosing.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

/**
 * 페이징 결과 정보 모델.
 */
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaginationResult<T> {

    /**
     * 페이징 정보.
     */
    @JsonProperty(value = "pageable")
    private Pageable pageable;

    /**
     * 게시물 목록.
     */
    @JsonProperty(value = "data")
    private List<T> data;
}
