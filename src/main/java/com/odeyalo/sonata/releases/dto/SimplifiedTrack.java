package com.odeyalo.sonata.releases.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SimplifiedTrack implements EntityType {
    String id;
    String name;
    // Url to get the full resource
    String href;
    Set<SimplifiedArtist> artists;
    @JsonProperty("duration_ms")
    long durationMs;
    @JsonProperty("disc_number")
    int discNumber;
    @JsonProperty("track_number")
    int trackNumber;
    @JsonProperty("is_playable")
    boolean isPlayable;
    final String type = "track";
}
