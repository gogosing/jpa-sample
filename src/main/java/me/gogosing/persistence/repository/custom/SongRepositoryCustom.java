package me.gogosing.persistence.repository.custom;

import me.gogosing.persistence.entity.SongEntity;

import java.util.List;

public interface SongRepositoryCustom {

    /**
     * 곡 목록 조회.
     * @param title 곡 제목 검색어.
     * @return 곡 엔티티 목록.
     */
    List<SongEntity> getSongEntities(String title);
}
