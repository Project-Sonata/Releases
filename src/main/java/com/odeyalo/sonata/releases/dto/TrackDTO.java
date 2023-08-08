package com.odeyalo.sonata.releases.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrackDTO {
    Long id;
    String name;
    long duration_ms;

}
