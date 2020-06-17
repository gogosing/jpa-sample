package me.gogosing.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

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
    @JsonProperty(value = "paging")
    private Paging paging;

    /**
     * 검색 필터 정보.
     */
    @JsonProperty(value = "filters")
    private Map<String, Object> filters;

    /**
     * 게시물 목록.
     */
    @JsonProperty(value = "data")
    private List<T> data;
}
