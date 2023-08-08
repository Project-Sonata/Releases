package com.odeyalo.sonata.releases.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.odeyalo.sonata.releases.entity.Release;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED)
public class ReleaseDto {
    String name;
    @JsonProperty("release_date")
    LocalDate releaseDate;
    @JsonProperty("release_date_precision")
    Release.ReleaseDatePrecision releaseDatePrecision;
    @JsonProperty("thumbnail_url")
    String thumbnailUrl;
    ReleaseType type;
    long durationMs;

    public enum ReleaseType {
        ALBUM
    }
}
