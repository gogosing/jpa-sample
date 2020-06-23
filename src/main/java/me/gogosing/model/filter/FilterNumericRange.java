package me.gogosing.model.filter;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 숫자 필터링 조건 모델.
 */
@Getter
@Setter
public class FilterNumericRange {

    /**
     * 필터링 적용 최소 수.
     */
    @JsonProperty(value = "min")
    private BigDecimal min;

    /**
     * 필터링 적용 최대 수.
     */
    @JsonProperty(value = "max")
    private BigDecimal max;
}
