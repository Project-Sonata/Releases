package com.odeyalo.sonata.releases.repository;

import com.odeyalo.sonata.releases.entity.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {

    Optional<Track> findByPublicId(String publicId);

}