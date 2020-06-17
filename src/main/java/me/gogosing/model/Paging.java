package me.gogosing.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 페이징 정보 모델.
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Paging {

    /**
     * 페이지 번호.
     */
    @Builder.Default
    @JsonProperty(value = "no")
    private int no = 1;

    /**
     * 페이지 당 조회 수.
     */
    @Builder.Default
    @JsonProperty(value = "limit")
    private int limit = 10;

    /**
     * 총 게시물 수.
     */
    @Builder.Default
    @JsonProperty(value = "total")
    private long total = 0;
}
