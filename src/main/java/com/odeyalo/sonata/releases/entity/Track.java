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
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "public_id", nullable = false, updatable = false, unique = true, length = 30)
    String publicId;
    @Column(name = "track_name", updatable = false, length = 300)
    String name;
    @ManyToOne
    Album album;
    @ManyToMany
    @ToString.Exclude
    @Singular
    Set<Artist> artists;
    @Column(name = "duration_ms", nullable = false)
    long durationMs;
    @Column(name = "preview_url")
    String previewUrl;
    @Embedded
    Order order;

    protected Track(TrackBuilder<?, ?> builder) {
        this.id = builder.id;
        this.publicId = builder.publicId;
        this.name = builder.name;
        this.album = builder.album;
        this.artists = new HashSet<>(builder.artists);
        this.durationMs = builder.durationMs;
        this.previewUrl = builder.previewUrl;
        this.order = builder.order;
    }

    @Data
    @AllArgsConstructor(staticName = "of")
    @Builder
    @NoArgsConstructor
    @Embeddable
    public static final class Order {
        @Column(name = "disc_number", nullable = false)
        int discNumber;
        @Column(name = "index", nullable = false)
        int index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Track track = (Track) o;
        return getId() != null && Objects.equals(getId(), track.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
