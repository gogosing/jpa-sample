package me.gogosing.persistence.repository.custom.impl;

import com.querydsl.jpa.JPQLQuery;
import me.gogosing.persistence.entity.AlbumEntity;
import me.gogosing.persistence.repository.custom.AlbumRepositoryCustom;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static me.gogosing.persistence.entity.QAlbumEntity.albumEntity;
import static me.gogosing.persistence.entity.QSongEntity.songEntity;

public class AlbumRepositoryCustomImpl extends QuerydslRepositorySupport
        implements AlbumRepositoryCustom, InitializingBean {

    public AlbumRepositoryCustomImpl() {
        super(AlbumEntity.class);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(getQuerydsl(), "The QueryDsl must not be null.");
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
                .where(albumEntity.deleted.isFalse());

        final long totalCount = query
                .select(albumEntity).distinct().fetchCount();

        List<AlbumEntity> results = Collections.emptyList();
        if (totalCount > 0L) {
            results = query
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .orderBy(albumEntity.createOn.desc())
                    .fetch();
        }

        return new PageImpl<>(results, pageable, totalCount);
    }
}
