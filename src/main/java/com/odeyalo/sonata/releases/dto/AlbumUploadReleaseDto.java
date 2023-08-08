package com.odeyalo.sonata.releases.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AlbumUploadReleaseDto {
    AlbumReleaseDto release;
}
