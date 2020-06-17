package me.gogosing.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.gogosing.model.Album;
import me.gogosing.model.Song;
import me.gogosing.persistence.entity.SongEntity;
import me.gogosing.persistence.repository.AlbumRepository;
import me.gogosing.persistence.repository.SongRepository;
import me.gogosing.service.SearchService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final AlbumRepository albumRepository;

    private final SongRepository songRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Album> getAlbumList(final String title) {
        return albumRepository.getAlbumEntities(title).stream().map(albumEntity -> {
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

    @Override
    @Transactional(readOnly = true)
    public List<Song> getSongList(final String title) {
        return songRepository.getSongEntities(title).stream().map(songEntity -> {
            final var song = new Song();
            song.setId(songEntity.getId());
            song.setTitle(songEntity.getTitle());
            song.setTrack(songEntity.getTrackNo());
            song.setLength(songEntity.getLength());
            return song;
        }).collect(Collectors.toList());
    }
}
