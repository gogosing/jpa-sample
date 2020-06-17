package me.gogosing.service.impl

import me.gogosing.component.GenerateIdComponent
import me.gogosing.model.Album
import me.gogosing.model.Song
import me.gogosing.persistence.entity.AlbumEntity
import me.gogosing.persistence.repository.AlbumRepository
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.junit.jupiter.SpringExtension
import spock.lang.Specification

@ExtendWith(SpringExtension.class)
class AlbumServiceImplTests extends Specification {

    def generateIdComponent = Mock(GenerateIdComponent.class)

    def albumRepository = Mock(AlbumRepository.class)

    def albumService

    def setup() {
        albumService = new AlbumServiceImpl(generateIdComponent, albumRepository)
    }

    def "앨범 정보 생성 테스트" () {
        given:
        def song1 = new Song()
        song1.setTitle("테스트 음원 1")
        song1.setTrack(1)
        song1.setLength(101)

        def song2 = new Song()
        song2.setTitle("테스트 음원 2")
        song2.setTrack(2)
        song2.setLength(102)

        def song3 = new Song()
        song3.setTitle("테스트 음원 3")
        song3.setTrack(3)
        song3.setLength(103)

        def album = new Album()
        album.setTitle("테스트 앨범")
        album.setSongs(List.of(song1, song2, song3))

        generateIdComponent.albumId() >> "FA200205000001"
        generateIdComponent.songId() >>> ["FS200205000001", "FS200205000002", "FS200205000003"]

        def capturedAlbumEntity
        albumRepository.save(_) >> { AlbumEntity albumEntity ->
            capturedAlbumEntity = albumEntity
        }

        when:
        def actualResult = albumService.createAlbum(album)

        then:
        actualResult == capturedAlbumEntity.getId()
    }
}