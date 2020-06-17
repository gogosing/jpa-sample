package me.gogosing.persistence.repository;

import me.gogosing.persistence.entity.AlbumEntity;
import me.gogosing.persistence.repository.custom.AlbumRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 앨범 Repository.
 */
@Repository
public interface AlbumRepository
        extends JpaRepository<AlbumEntity, Long>, AlbumRepositoryCustom {
}
