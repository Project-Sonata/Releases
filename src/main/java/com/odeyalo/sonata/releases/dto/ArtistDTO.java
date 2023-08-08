package com.odeyalo.sonata.releases.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ArtistDTO implements EntityType {
    String id;
    String name;
    String href;
    final String type = "artist";
}
