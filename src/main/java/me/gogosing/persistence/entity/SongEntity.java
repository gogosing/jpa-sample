package me.gogosing.persistence.entity;

import lombok.Getter;
import lombok.Setter;
import me.gogosing.persistence.converter.BooleanToCharConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.ZonedDateTime;

/**
 * 곡 정보 Entity.
 */
@Setter
@Getter
@Entity
@Table(
        name = "SONG",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"SONG_ID"})}
)
public class SongEntity {

    /**
     * 레코드 식별자.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SONG_KEY")
    private Long key = null;

    /**
     * 레코드 대체 식별자.
     */
    @Column(name = "SONG_ID", nullable = false, columnDefinition = "char(14)")
    private String id;

    /**
     * 소속 앨범 레코드 식별자.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ALBUM_KEY", referencedColumnName = "ALBUM_KEY", nullable = false)
    private AlbumEntity albumEntity;

    /**
     * 제목.
     */
    @Column(name = "TITLE", nullable = false, length = 150)
    private String title;

    /**
     * 곡 길이 (단위: 초(second)).
     */
    @Column(name = "LENGTH", nullable = false)
    private int length;

    /**
     * 트랙 번호.
     */
    @Column(name = "TRACK_NO", nullable = false, columnDefinition = "tinyint")
    private int trackNo;

    /**
     * 삭제여부.
     */
    @Convert(converter = BooleanToCharConverter.class)
    @Column(name = "DELETED", nullable = false, columnDefinition = "char(1)")
    private boolean deleted = false;

    /**
     * 레코드 생성일시.
     */
    @Column(name = "CREATE_UTC", nullable = false, updatable = false)
    private ZonedDateTime createOn;
}
