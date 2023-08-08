package com.odeyalo.sonata.releases.repository;

import com.odeyalo.sonata.releases.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

    Optional<Album> findByPublicId(String id);
}