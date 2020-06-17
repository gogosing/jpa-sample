package me.gogosing.persistence.repository.custom.impl;

import com.querydsl.jpa.JPQLQuery;
import me.gogosing.persistence.entity.QAlbumEntity;
import me.gogosing.persistence.entity.QSongEntity;
import me.gogosing.persistence.entity.SongEntity;
import me.gogosing.persistence.repository.custom.SongRepositoryCustom;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class SongRepositoryCustomImpl extends QuerydslRepositorySupport implements SongRepositoryCustom {

    private final QSongEntity songEntity = QSongEntity.songEntity;
    private final QAlbumEntity albumEntity = QAlbumEntity.albumEntity;

    public SongRepositoryCustomImpl() {
        super(SongEntity.class);
    }

    @Override
    public List<SongEntity> getSongEntities(final String title) {
        JPQLQuery<SongEntity> query = from(songEntity)
                .innerJoin(songEntity.albumEntity, albumEntity)
                .fetchJoin()
                .where(
                        songEntity.deleted.isFalse(),
                        albumEntity.deleted.isFalse(),
                        songEntity.title.containsIgnoreCase(title)
                );

        return query.orderBy(songEntity.title.asc()).fetch();
    }
}
