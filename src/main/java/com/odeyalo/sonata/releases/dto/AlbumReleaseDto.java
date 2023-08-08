package com.odeyalo.sonata.releases.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.odeyalo.sonata.releases.entity.Album;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AlbumReleaseDto extends ReleaseDto {
    List<String> genres;
    @JsonProperty("artists")
    List<String> artistIds;
    @JsonProperty("album_type")
    Album.AlbumType albumType;
    List<UploadTrackDto> tracks;
    // Name of the file that contains thumbnail for the release
    @JsonProperty("total_track_count")
    int totalTrackCount;
}