package me.gogosing.persistence.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.gogosing.persistence.converter.BooleanToCharConverter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 앨범 정보 Entity.
 */
@Setter
@Getter
@Entity
@Table(
    name = "ALBUM",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"ALBUM_ID"})}
)
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class AlbumEntity implements Serializable {

    /**
     * 레코드 식별자.
     */
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ALBUM_KEY")
    private Long key = null;

    /**
     * 레코드 대체 식별자.
     */
    @EqualsAndHashCode.Include
    @Column(name = "ALBUM_ID", nullable = false, columnDefinition = "char(14)")
    private String id;

    /**
     * 제목.
     */
    @EqualsAndHashCode.Include
    @Column(name = "TITLE", nullable = false, length = 150)
    private String title;

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

    /**
     * 앨범 소속 곡 목록.
     */
    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(
            mappedBy = "albumEntity",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = true
    )
    private Set<SongEntity> songEntities = new LinkedHashSet<>();

    @Builder
    public AlbumEntity(Long key, String id, String title, boolean deleted,
                       ZonedDateTime createOn, Set<SongEntity> songEntities) {
        this.key = key;
        this.id = id;
        this.title = title;
        this.deleted = deleted;
        this.createOn = createOn;
        this.songEntities = songEntities;
    }
}
