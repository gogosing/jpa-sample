package me.gogosing.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 곡 정보 모델.
 */
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Song {

    /**
     * 곡 레코드 대체 식별자.
     */
    @JsonProperty(value = "id")
    private String id;

    /**
     * 제목.
     */
    @JsonProperty(value = "title")
    private String title;

    /**
     * 트랙 번호.
     */
    @JsonProperty(value = "track")
    private int track;

    /**
     * 길이.
     */
    @JsonProperty(value = "length")
    private int length;
}
