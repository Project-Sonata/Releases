package com.odeyalo.sonata.releases.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "albums", indexes = @Index(columnList = "public_id"))
public class Album extends Release {
    @ManyToMany
    @ToString.Exclude
    Set<Artist> artists;
    @OneToMany(cascade = CascadeType.ALL)
    @ToString.Exclude
    Set<Track> tracks;
    @Column(name = "total_track_count", nullable = false)
    int totalTrackCount;
    @Column(name = "album_type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    AlbumType albumType;

    public Album(ReleaseBuilder<?, ?> b, Set<Artist> artists, Set<Track> tracks, int totalTrackCount, AlbumType albumType) {
        super(b);
        this.artists = artists;
        this.tracks = tracks;
        this.totalTrackCount = totalTrackCount;
        this.albumType = albumType;
    }

    public enum AlbumType {
        /**
         * If duration is more than 30 minutes
         */
        ALBUM,
        /**
         * 4-6 tracks with duration less than 30 minutes
         */
        EPISODE,
        /**
         * 1-3 songs that less than 30 minutes
         */
        SINGLE
    }

    public void addTrack(Track track) {
        if (tracks == null) {
            this.tracks = new HashSet<>();
        }
        track.setAlbum(this);
        this.tracks.add(track);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Album album = (Album) o;
        return getId() != null && Objects.equals(getId(), album.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
