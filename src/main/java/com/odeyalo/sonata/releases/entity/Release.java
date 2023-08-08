package com.odeyalo.sonata.releases.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapProperties;

import java.time.LocalDate;
import java.util.Set;

@MappedSuperclass
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Release {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "public_id", nullable = false, updatable = false, length = 30)
    String publicId;
    @Column(name = "name", updatable = false, length = 300)
    String name;
    @Column(name = "release_date", nullable = false)
    LocalDate releaseDate;
    @Column(name = "release_date_precision", nullable = false)
    @Enumerated(value = EnumType.STRING)
    ReleaseDatePrecision releaseDatePrecision;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "images")
    Set<ImageInfo> images;
    @Column(name = "duration_ms", nullable = false)
    long durationMs;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "release_type", nullable = false)
    ReleaseType releaseType;

    public enum ReleaseType {
        ALBUM,
        // NOT SUPPORTED YET
        PODCAST,
        // NOT SUPPORTED YET
        AUDIO_BOOK
    }

    public enum ReleaseDatePrecision {
        /**
         * Day, month and year are known about release. Example: 2022-11-21
         */
        DAY,
        /**
         * Month and year are known about release. Example: 2021-11
         */
        MONTH,
        /**
         * Only year known about release. Example: 2021
         */
        YEAR
    }
}
