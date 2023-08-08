package com.odeyalo.sonata.releases.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SimplifiedArtist implements EntityType {
    String id;
    String name;
    String href;
    final String type = "artist";
}
