package com.odeyalo.sonata.releases.repository;

import com.odeyalo.sonata.releases.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {

    Optional<Artist> findByPublicId(String publicId);
}