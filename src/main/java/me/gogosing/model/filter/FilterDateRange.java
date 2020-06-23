package me.gogosing.model.filter;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 기간 필터링 조건 모델.
 */
@Getter
@Setter
public class FilterDateRange {

    /**
     * 필터링 적용 시작일 (해당 일 포함).
     */
    @JsonProperty(value = "from")
    private LocalDateTime from;

    /**
     * 필터링 적용 종료일 (해당 일 포함).
     */
    @JsonProperty(value = "to")
    private LocalDateTime to;
}
