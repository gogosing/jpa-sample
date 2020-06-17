package me.gogosing.persistence.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
import java.io.Serializable;
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
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class SongEntity implements Serializable {

    /**
     * 레코드 식별자.
     */
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SONG_KEY")
    private Long key = null;

    /**
     * 레코드 대체 식별자.
     */
    @EqualsAndHashCode.Include
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
    @EqualsAndHashCode.Include
    @Column(name = "TITLE", nullable = false, length = 150)
    private String title;

    /**
     * 곡 길이 (단위: 초(second)).
     */
    @EqualsAndHashCode.Include
    @Column(name = "LENGTH", nullable = false)
    private int length;

    /**
     * 트랙 번호.
     */
    @EqualsAndHashCode.Include
    @Column(name = "TRACK_NO", nullable = false, columnDefinition = "tinyint")
    private int trackNo;

    /**
     * 삭제여부.
     */
    @EqualsAndHashCode.Include
    @Convert(converter = BooleanToCharConverter.class)
    @Column(name = "DELETED", nullable = false, columnDefinition = "char(1)")
    private boolean deleted = false;

    /**
     * 레코드 생성일시.
     */
    @EqualsAndHashCode.Include
    @Column(name = "CREATE_UTC", nullable = false, updatable = false)
    private ZonedDateTime createOn;

    @Builder
    public SongEntity(Long key, String id, AlbumEntity albumEntity, String title,
                      int length, int trackNo, boolean deleted, ZonedDateTime createOn) {
        this.key = key;
        this.id = id;
        this.albumEntity = albumEntity;
        this.length = length;
        this.trackNo = trackNo;
        this.deleted = deleted;
        this.createOn = createOn;
    }
}
