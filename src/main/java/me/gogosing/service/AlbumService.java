package me.gogosing.service;

import me.gogosing.model.Album;
import me.gogosing.model.PaginationResult;
import org.springframework.data.domain.Pageable;

/**
 * 앨범 서비스 Interface.
 */
public interface AlbumService {

    /**
     * 앨범 정보 생성.
     * @param album 등록 앨범 정보.
     * @return 생성된 앨범의 레코드 대체 식별자.
     */
    String createAlbum(Album album);

    /**
     * 특정 앨범 정보 조회.
     * @param albumId 앨범 레코드 대체 식별자.
     * @return 앨범 정보.
     */
    Album getAlbum(String albumId);

    /**
     * 페이징 처리된 앨범 목록 조회.
     * @param pageable 페이징 정보.
     * @return 페이징 처리된 앨범 목록 정보.
     */
    PaginationResult<Album> getAlbumPagination(Pageable pageable);

    /**
     * 앨범 정보 수정.
     * @param albumId 앨범 레코드 대체 식별자.
     * @param album 앨범 정보.
     */
    void updateAlbum(String albumId, Album album);

    /**
     * 앨범 정보 삭제.
     * @param albumId 앨범 레코드 대체 식별자.
     */
    void deleteAlbum(String albumId);
}
