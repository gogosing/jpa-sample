package me.gogosing.persistence.repository.custom;

import me.gogosing.persistence.entity.AlbumEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AlbumRepositoryCustom {

    /**
     * 특정 앨범 정보 조회.
     * @param albumId 앨범 레코드 대체 식별자.
     * @return 앨범 정보.
     */
    Optional<AlbumEntity> getAlbumEntity(String albumId);

    /**
     * 앨범 목록 조회.
     * @param title 앨범 타이틀 검색어.
     * @return 앨범 엔티티 목록.
     */
    List<AlbumEntity> getAlbumEntities(String title);

    /**
     * 페이징 처리된 앨범 목록 조회.
     * @param pageable 페이징 조건.
     * @return 페이징 처리된 앨범 목록.
     */
    Page<AlbumEntity> getPaginatedAlbumEntities(Pageable pageable);
}
