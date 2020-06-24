package me.gogosing.controller;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.gogosing.model.Album;
import me.gogosing.model.PaginationResult;
import me.gogosing.service.AlbumService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 앨범 관련 Controller.
 */
@Slf4j
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class AlbumController {

    private final AlbumService albumService;

    /**
     * 앨범 정보 생성.
     * @param album 등록 앨범 정보.
     * @return 생성된 앨범의 레코드 대체 식별자.
     */
    @PostMapping("/album")
    public Map<String, String> createAlbum(@RequestBody Album album) {
        return Map.of("id", albumService.createAlbum(album));
    }

    /**
     * 특정 앨범 정보 조회.
     * @param albumId 앨범 레코드 대체 식별자.
     * @return 앨범 정보.
     */
    @GetMapping("/album/{albumId}")
    public Album getAlbum(@PathVariable String albumId) {
        return albumService.getAlbum(albumId);
    }

    /**
     * 페이징 처리된 앨범 목록 조회.
     * @param pageable 페이징 정보.
     * @return 페이징 처리된 앨범 목록.
     */
    @PostMapping("/albums")
    public PaginationResult getAlbumPagination(
            @PageableDefault(
                sort = {"createOn"},
                direction = Direction.DESC,
                page = 0, size = 10
            ) Pageable pageable
    ) {
        return albumService.getAlbumPagination(pageable);
    }

    /**
     * 특정 앨범 정보 수정.
     * @param albumId 앨범 레코드 대체 식별자.
     * @param album 앨범 수정 정보.
     */
    @PutMapping("/album/{albumId}")
    public void updateAlbum(
            @PathVariable String albumId,
            @RequestBody Album album
    ) {
        albumService.updateAlbum(albumId, album);
    }

    /**
     * 특정 앨범 정보 삭제.
     * @param albumId 앨범 레코드 대체 식별자.
     */
    @DeleteMapping("/album/{albumId}")
    public void deleteAlbum(@PathVariable String albumId) {
        albumService.deleteAlbum(albumId);
    }
}
