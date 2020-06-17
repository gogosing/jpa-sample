package me.gogosing.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * 앨범 정보 모델.
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Album {

    /**
     * 앨범 레코드 대체 식별자.
     */
    @JsonProperty(value = "id")
    private String id;

    /**
     * 제목.
     */
    @JsonProperty(value = "title")
    private String title;

    /**
     * 수록곡 목록.
     */
    @JsonProperty(value = "songs")
    private List<Song> songs;
}
