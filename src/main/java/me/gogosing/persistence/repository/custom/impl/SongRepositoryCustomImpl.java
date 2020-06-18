package me.gogosing.persistence.repository.custom.impl;

import com.querydsl.jpa.JPQLQuery;
import me.gogosing.persistence.entity.SongEntity;
import me.gogosing.persistence.repository.custom.SongRepositoryCustom;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.Assert;

import java.util.List;

import static me.gogosing.persistence.entity.QAlbumEntity.albumEntity;
import static me.gogosing.persistence.entity.QSongEntity.songEntity;

public class SongRepositoryCustomImpl extends QuerydslRepositorySupport
        implements SongRepositoryCustom, InitializingBean {

    public SongRepositoryCustomImpl() {
        super(SongEntity.class);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(getQuerydsl(), "The QueryDsl must not be null.");
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
