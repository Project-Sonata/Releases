package com.odeyalo.sonata.releases.service.album.upload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@AllArgsConstructor(staticName = "of")
@Builder
public class UploadResult {
    boolean success;
    // Id of the entity that was saved
    String id;
    // Will be present if success is false
    String exceptionReason;

    public static UploadResult success(String id) {
        return of(true, id, null);
    }

    public static UploadResult failed(String exceptionReason) {
        return of(false, null, exceptionReason);
    }
}
