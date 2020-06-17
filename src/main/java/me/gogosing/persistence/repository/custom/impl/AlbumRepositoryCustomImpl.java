package me.gogosing.persistence.repository.custom.impl;

import com.querydsl.jpa.JPQLQuery;
import me.gogosing.persistence.entity.AlbumEntity;
import me.gogosing.persistence.entity.QAlbumEntity;
import me.gogosing.persistence.entity.QSongEntity;
import me.gogosing.persistence.repository.custom.AlbumRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Optional;

public class AlbumRepositoryCustomImpl extends QuerydslRepositorySupport implements AlbumRepositoryCustom {

    private final QAlbumEntity albumEntity = QAlbumEntity.albumEntity;
    private final QSongEntity songEntity = QSongEntity.songEntity;

    public AlbumRepositoryCustomImpl() {
        super(AlbumEntity.class);
    }

    @Override
    public Optional<AlbumEntity> getAlbumEntity(String albumId) {
        return Optional.ofNullable(
                from(albumEntity)
                    .leftJoin(albumEntity.songEntities, songEntity)
                    .fetchJoin()
                    .where(
                            albumEntity.deleted.isFalse(),
                            albumEntity.id.eq(albumId)
                    )
                    .fetchOne()
        );
    }

    @Override
    public List<AlbumEntity> getAlbumEntities(final String title) {
        JPQLQuery<AlbumEntity> query = from(albumEntity)
                .leftJoin(albumEntity.songEntities, songEntity)
                .fetchJoin()
                .where(
                        albumEntity.deleted.isFalse(),
                        albumEntity.title.containsIgnoreCase(title)
                );
        return query.orderBy(albumEntity.title.asc()).fetch();
    }

    @Override
    public Page<AlbumEntity> getPaginatedAlbumEntities(Pageable pageable) {
        JPQLQuery<AlbumEntity> query = from(albumEntity)
                .where(
                        albumEntity.deleted.isFalse()
                );

        List<AlbumEntity> results = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(albumEntity.createOn.desc())
                .fetch();

        long totalCount = 0L;
        if (!results.isEmpty()) {
            totalCount = query.select(albumEntity).distinct().fetchCount();
        }

        return new PageImpl<>(results, pageable, totalCount);
    }
}
