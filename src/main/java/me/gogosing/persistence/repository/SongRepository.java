package me.gogosing.persistence.repository;

import me.gogosing.persistence.entity.SongEntity;
import me.gogosing.persistence.repository.custom.SongRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 곡 Repository.
 */
@Repository
public interface SongRepository
        extends JpaRepository<SongEntity, Long>, SongRepositoryCustom {
}
