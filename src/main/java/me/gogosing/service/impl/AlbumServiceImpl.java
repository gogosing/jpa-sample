package me.gogosing.service.impl;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.gogosing.component.GenerateIdComponent;
import me.gogosing.exception.NotFoundException;
import me.gogosing.model.Album;
import me.gogosing.model.PaginationResult;
import me.gogosing.model.Song;
import me.gogosing.persistence.entity.AlbumEntity;
import me.gogosing.persistence.entity.SongEntity;
import me.gogosing.persistence.repository.AlbumRepository;
import me.gogosing.service.AlbumService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlbumServiceImpl implements AlbumService {

    private final GenerateIdComponent generateIdComponent;

    private final AlbumRepository albumRepository;

    @Override
    @Transactional
    public String createAlbum(final Album album) {
        final var albumEntity = new AlbumEntity();

        albumEntity.setId(generateIdComponent.albumId());
        albumEntity.setTitle(album.getTitle());
        albumEntity.setCreateOn(ZonedDateTime.now());

        final var songEntities = new LinkedHashSet<SongEntity>();

        album.getSongs().forEach(song -> {
            final var songEntity = new SongEntity();
            songEntity.setId(generateIdComponent.songId());
            songEntity.setAlbumEntity(albumEntity);
            songEntity.setTitle(song.getTitle());
            songEntity.setLength(song.getLength());
            songEntity.setTrackNo(song.getTrack());
            songEntity.setCreateOn(ZonedDateTime.now());
            songEntities.add(songEntity);
        });
        albumEntity.getSongEntities().addAll(songEntities);

        return albumRepository.save(albumEntity).getId();
    }

    @Override
    @Transactional(readOnly = true)
    public Album getAlbum(final String albumId) {
        final var albumEntity = getAlbumEntity(albumId);
        return Album.builder()
                .id(albumEntity.getId())
                .title(albumEntity.getTitle())
                .songs(albumEntity.getSongEntities().stream()
                        .sorted(Comparator.comparingInt(SongEntity::getTrackNo))
                        .map(songEntity -> {
                            final var song = new Song();
                            song.setId(songEntity.getId());
                            song.setTitle(songEntity.getTitle());
                            song.setTrack(songEntity.getTrackNo());
                            song.setLength(songEntity.getLength());
                            return song;
                        }).collect(Collectors.toList()))
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public PaginationResult<Album> getAlbumPagination(final Pageable pageable) {
        final var paginatedResult = albumRepository.getPaginatedAlbumEntities(pageable);

        final var totalElements = paginatedResult.getTotalElements();

        List<Album> albumList = Collections.emptyList();
        if (totalElements > 0L) {
            albumList = paginatedResult.getContent().stream().map(albumEntity -> {
                final var album = new Album();
                album.setId(albumEntity.getId());
                album.setTitle(albumEntity.getTitle());
                album.setSongs(albumEntity.getSongEntities().stream()
                        .sorted(Comparator.comparingInt(SongEntity::getTrackNo))
                        .map(songEntity -> {
                            final var song = new Song();
                            song.setId(songEntity.getId());
                            song.setTitle(songEntity.getTitle());
                            song.setTrack(songEntity.getTrackNo());
                            song.setLength(songEntity.getLength());
                            return song;
                        }).collect(Collectors.toList()));
                return album;
            }).collect(Collectors.toList());
        }

        final var paginationResult = new PaginationResult<Album>();
        paginationResult.setPageable(pageable);
        paginationResult.setData(albumList);

        return paginationResult;
    }

    @Override
    @Transactional
    public void updateAlbum(final String albumId, final Album album) {
        final var albumEntity = getAlbumEntity(albumId);

        final var songEntitiesMap = albumEntity.getSongEntities().stream()
                .collect(Collectors.toMap(SongEntity::getId, songEntity -> songEntity));

        final var songEntities = album.getSongs().stream().map(song -> {
            final var optionalSong = Optional
                    .ofNullable(songEntitiesMap.get(song.getId()));

            SongEntity songEntity;
            if (optionalSong.isPresent()) {
                songEntity = optionalSong.get();
                songEntity.setTitle(song.getTitle());
                songEntity.setLength(song.getLength());
                songEntity.setTrackNo(song.getTrack());
            } else {
                songEntity = new SongEntity();
                songEntity.setId(generateIdComponent.songId());
                songEntity.setAlbumEntity(albumEntity);
                songEntity.setTitle(song.getTitle());
                songEntity.setLength(song.getLength());
                songEntity.setTrackNo(song.getTrack());
                songEntity.setCreateOn(ZonedDateTime.now());
            }
            return songEntity;
        }).collect(Collectors.toCollection(LinkedHashSet::new));

        albumEntity.setTitle(album.getTitle());
        albumEntity.getSongEntities().clear();
        albumEntity.getSongEntities().addAll(songEntities);

        albumRepository.save(albumEntity);
    }

    @Override
    @Transactional
    public void deleteAlbum(final String albumId) {
        final var albumEntity = getAlbumEntity(albumId);

        // 컬럼값 기반의 삭제 처리일 경우
        /*
        albumEntity.setDeleted(true);
        albumRepository.save(albumEntity);
        */

        // 실제 record 제거 처리일 경우
        albumRepository.delete(albumEntity);
    }

    /**
     * 특정 앨범 엔티티 조회.
     * @param albumId 앨범 레코드 대체 식별자.
     * @return 조회된 앨범 엔티티.
     */
    private AlbumEntity getAlbumEntity(final String albumId) {
        return albumRepository.getAlbumEntity(albumId).orElseThrow(() ->
                new NotFoundException(String.format("앨범[%s]은 존재하지 않거나 삭제된 상태입니다.", albumId))
        );
    }
}
