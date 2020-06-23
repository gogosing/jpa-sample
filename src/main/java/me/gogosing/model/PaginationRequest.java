package me.gogosing.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import me.gogosing.jackson.PagingFilterDeserializer;

import java.util.Map;

/**
 * 페이징 요청 정보 모델.
 */
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaginationRequest {

    /**
     * 페이징 정보.
     */
    @JsonProperty(value = "paging")
    private Paging paging;

    /**
     * 검색 필터 정보.
     */
    @JsonDeserialize(using = PagingFilterDeserializer.class)
    @JsonProperty(value = "filters")
    private Map<String, Object> filters;
}
