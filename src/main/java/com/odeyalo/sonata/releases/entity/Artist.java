package com.odeyalo.sonata.releases.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "artists", indexes = @Index(columnList = "public_id"))
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    // Can be null if artist does not registered in Sonata, but is part of the album or track performers
    @Column(name = "public_id")
    String publicId;
    @Column(name = "artist_name", nullable = false)
    String artistName;
}
