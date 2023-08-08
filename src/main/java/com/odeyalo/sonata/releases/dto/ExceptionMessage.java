package com.odeyalo.sonata.releases.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor(staticName = "empty")
public class ExceptionMessage {
    String message;
}
