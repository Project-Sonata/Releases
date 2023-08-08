package com.odeyalo.sonata.releases.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UploadTrackDto {
    String name;
    @JsonProperty("disc_number")
    int discNumber;
    int index;
    @JsonProperty("track_url")
    String trackUrl;
}
