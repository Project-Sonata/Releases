package com.odeyalo.sonata.releases.dto;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AlbumDTO implements EntityType {
    String id;
    String name;
    @JsonProperty("release_date")
    LocalDate releaseDate;
    @JsonProperty("release_date_precision")
    String releaseDatePrecision;
    @JsonProperty("images")
    Images images;
    @JsonProperty("duration_ms")
    long durationMs;
    @JsonProperty("release_type")
    String releaseType;
    Set<ArtistDTO> artists;
    Set<SimplifiedTrack> tracks;
    @JsonProperty("total_track_count")
    int totalTrackCount;
    @JsonProperty("album_type")
    String albumType;

    final String type = "album";
}


